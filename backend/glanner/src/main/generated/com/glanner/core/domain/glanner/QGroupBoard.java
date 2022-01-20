package com.glanner.core.domain.glanner;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroupBoard is a Querydsl query type for GroupBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupBoard extends EntityPathBase<GroupBoard> {

    private static final long serialVersionUID = -801432263L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroupBoard groupBoard = new QGroupBoard("groupBoard");

    public final com.glanner.core.domain.base.QBaseTimeEntity _super = new com.glanner.core.domain.base.QBaseTimeEntity(this);

    public final ListPath<com.glanner.core.domain.board.Comment, com.glanner.core.domain.board.QComment> comments = this.<com.glanner.core.domain.board.Comment, com.glanner.core.domain.board.QComment>createList("comments", com.glanner.core.domain.board.Comment.class, com.glanner.core.domain.board.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath fileUrls = createString("fileUrls");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath interests = createString("interests");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath title = createString("title");

    public final com.glanner.core.domain.user.QUser user;

    public QGroupBoard(String variable) {
        this(GroupBoard.class, forVariable(variable), INITS);
    }

    public QGroupBoard(Path<? extends GroupBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroupBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroupBoard(PathMetadata metadata, PathInits inits) {
        this(GroupBoard.class, metadata, inits);
    }

    public QGroupBoard(Class<? extends GroupBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.glanner.core.domain.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

