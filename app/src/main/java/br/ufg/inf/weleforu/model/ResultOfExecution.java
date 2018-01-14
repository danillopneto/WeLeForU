package br.ufg.inf.weleforu.model;

/**
 * Created by danil on 1/14/2018.
 */

public class ResultOfExecution {
    private boolean _success;
    private String _message;

    public ResultOfExecution(boolean success, String message) {
        this._success = success;
        this._message = message;
    }

    public boolean isSuccess() {
        return this._success;
    }

    public String getMessage() {
        return this._message;
    }
}
