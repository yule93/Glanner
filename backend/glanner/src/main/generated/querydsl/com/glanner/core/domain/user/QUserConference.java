package com.glanner.core.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserConference is a Querydsl query type for UserConference
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserConference extends EntityPathBase<UserConference> {

    private static final long serialVersionUID = 487178909L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserConference userConference = new QUserConference("userConference");

    public final com.glanner.core.domain.base.QBaseTimeEntity _super = new com.glanner.core.domain.base.QBaseTimeEntity(this);

    public final QConference conference;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final QUser user;

    public QUserConference(String variable) {
        this(UserConference.class, forVariable(variable), INITS);
    }

    public QUserConference(Path<? extends UserConference> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserConference(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserConference(PathMetadata metadata, PathInits inits) {
        this(UserConference.class, metadata, inits);
    }

    public QUserConference(Class<? extends UserConference> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.conference = inits.isInitialized("conference") ? new QConference(forProperty("conference"), inits.get("conference")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

