package com.glanner.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glanner.api.dto.request.SendMailReqDto;
import com.glanner.api.dto.request.SendSmsApiReqDto;
import com.glanner.api.dto.request.SendSmsReqDto;
import com.glanner.api.dto.response.SendSmsApiResDto;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class NotificationServiceTest {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${sms.serviceid}")
    private String serviceId;
    @Value("${sms.accesskey}")
    private String accessKey;
    @Value("${sms.secretkey}")
    private String secretKey;

    @Test
    public void testSendMail() throws Exception{
        //given
        SendMailReqDto reqDto = new SendMailReqDto("aldlswjddma@naver.com", "title", "Content");

        //when
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(reqDto.getAddress());
        msg.setSubject(reqDto.getTitle());
        msg.setText(reqDto.getContent());
        javaMailSender.send(msg);
    }

    @Test
    public void testSendSms() throws Exception{
        //given
        SendSmsReqDto reqDto = new SendSmsReqDto("01087973122", "테스트");
        //when
        Long time = System.currentTimeMillis();
        List<SendSmsReqDto> messages = new ArrayList<>();
        messages.add(reqDto);

        SendSmsApiReqDto smsRequest = new SendSmsApiReqDto("SMS", "COMM", "82", "01034033122", "테스트", messages);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(smsRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", this.accessKey);
        String sig = makeSignature(time); //암호화
        headers.set("x-ncp-apigw-signature-v2", sig);

        HttpEntity<String> body = new HttpEntity<>(jsonBody,headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+this.serviceId+"/messages"), body, SendSmsApiResDto.class);
        //then
    }

    public String makeSignature(Long time) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/"+ this.serviceId+"/messages";
        String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }
}