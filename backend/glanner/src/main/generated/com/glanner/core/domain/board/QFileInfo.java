package com.glanner.core.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFileInfo is a Querydsl query type for FileInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileInfo extends EntityPathBase<FileInfo> {

    private static final long serialVersionUID = -860903457L;

    public static final QFileInfo fileInfo = new QFileInfo("fileInfo");

    public final SimplePath<Board> board = createSimple("board", Board.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath originFile = createString("originFile");

    public final StringPath saveFile = createString("saveFile");

    public final StringPath saveFolder = createString("saveFolder");

    public QFileInfo(String variable) {
        super(FileInfo.class, forVariable(variable));
    }

    public QFileInfo(Path<? extends FileInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFileInfo(PathMetadata metadata) {
        super(FileInfo.class, metadata);
    }

}

