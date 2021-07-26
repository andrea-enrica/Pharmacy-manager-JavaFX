package org.example.domain;

public interface IUndoRedoOperation {
    void doUndo() throws Exception;
    void doRedo() throws Exception;
}
