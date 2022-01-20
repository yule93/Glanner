package com.glanner.core.domain.glanner;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGlanner is a Querydsl query type for Glanner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGlanner extends EntityPathBase<Glanner> {

    private static final long serialVersionUID = 96479287L;

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
    }

}

