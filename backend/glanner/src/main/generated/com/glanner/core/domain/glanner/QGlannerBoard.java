package com.glanner.core.domain.glanner;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGlannerBoard is a Querydsl query type for GlannerBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGlannerBoard extends EntityPathBase<GlannerBoard> {

    private static final long serialVersionUID = 902389999L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGlannerBoard glannerBoard = new QGlannerBoard("glannerBoard");

    public final com.glanner.core.domain.base.QBaseTimeEntity _super = new com.glanner.core.domain.base.QBaseTimeEntity(this);

    public final ListPath<com.glanner.core.domain.board.Comment, SimplePath<com.glanner.core.domain.board.Comment>> comments = this.<com.glanner.core.domain.board.Comment, SimplePath<com.glanner.core.domain.board.Comment>>createList("comments", com.glanner.core.domain.board.Comment.class, SimplePath.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath fileUrls = createString("fileUrls");

    public final QGlanner glanner;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath title = createString("title");

    public final com.glanner.core.domain.user.QUser user;

    public QGlannerBoard(String variable) {
        this(GlannerBoard.class, forVariable(variable), INITS);
    }

    public QGlannerBoard(Path<? extends GlannerBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGlannerBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGlannerBoard(PathMetadata metadata, PathInits inits) {
        this(GlannerBoard.class, metadata, inits);
    }

    public QGlannerBoard(Class<? extends GlannerBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.glanner = inits.isInitialized("glanner") ? new QGlanner(forProperty("glanner"), inits.get("glanner")) : null;
        this.user = inits.isInitialized("user") ? new com.glanner.core.domain.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

