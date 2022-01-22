package com.glanner.core.domain.glanner;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
<<<<<<< HEAD
=======
import com.querydsl.core.types.dsl.PathInits;
>>>>>>> hotfix/backend/glanner_domain


/**
 * QGlanner is a Querydsl query type for Glanner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGlanner extends EntityPathBase<Glanner> {

    private static final long serialVersionUID = 96479287L;

<<<<<<< HEAD
    public static final QGlanner glanner = new QGlanner("glanner");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QGlanner(String variable) {
        super(Glanner.class, forVariable(variable));
    }

    public QGlanner(Path<? extends Glanner> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGlanner(PathMetadata metadata) {
        super(Glanner.class, metadata);
=======
    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGlanner glanner = new QGlanner("glanner");

    public final com.glanner.core.domain.base.QBaseTimeEntity _super = new com.glanner.core.domain.base.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<DailyWorkGlanner, QDailyWorkGlanner> dailyWorks = this.<DailyWorkGlanner, QDailyWorkGlanner>createList("dailyWorks", DailyWorkGlanner.class, QDailyWorkGlanner.class, PathInits.DIRECT2);

    public final ListPath<GlannerBoard, QGlannerBoard> glannerBoards = this.<GlannerBoard, QGlannerBoard>createList("glannerBoards", GlannerBoard.class, QGlannerBoard.class, PathInits.DIRECT2);

    public final com.glanner.core.domain.user.QUser host;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final ListPath<UserGlanner, QUserGlanner> userGlanners = this.<UserGlanner, QUserGlanner>createList("userGlanners", UserGlanner.class, QUserGlanner.class, PathInits.DIRECT2);

    public QGlanner(String variable) {
        this(Glanner.class, forVariable(variable), INITS);
    }

    public QGlanner(Path<? extends Glanner> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGlanner(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGlanner(PathMetadata metadata, PathInits inits) {
        this(Glanner.class, metadata, inits);
    }

    public QGlanner(Class<? extends Glanner> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.host = inits.isInitialized("host") ? new com.glanner.core.domain.user.QUser(forProperty("host"), inits.get("host")) : null;
>>>>>>> hotfix/backend/glanner_domain
    }

}

