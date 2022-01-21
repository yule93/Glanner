package com.glanner.core.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreeBoard is a Querydsl query type for FreeBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFreeBoard extends EntityPathBase<FreeBoard> {

    private static final long serialVersionUID = -41810203L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreeBoard freeBoard = new QFreeBoard("freeBoard");

    public final com.glanner.core.domain.base.QBaseTimeEntity _super = new com.glanner.core.domain.base.QBaseTimeEntity(this);

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Integer> disLikeCount = createNumber("disLikeCount", Integer.class);

    public final StringPath fileUrls = createString("fileUrls");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath title = createString("title");

    public final com.glanner.core.domain.user.QUser user;

    public QFreeBoard(String variable) {
        this(FreeBoard.class, forVariable(variable), INITS);
    }

    public QFreeBoard(Path<? extends FreeBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreeBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreeBoard(PathMetadata metadata, PathInits inits) {
        this(FreeBoard.class, metadata, inits);
    }

    public QFreeBoard(Class<? extends FreeBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.glanner.core.domain.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

