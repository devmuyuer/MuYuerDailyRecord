package yao.muyuer.dailyrecord.entity;

/**
 * Created by DevMuyuer on 2017-09-14.
 */

public class SystemEntity {
    private int _id;
    private String _userId;
    private String _userName;
    private String _userPassWord;
    private byte[] _userLogo;

    public void SetId(int _id) {
        this._id = _id;
    }
    public int GetId() {
        return _id;
    }
    public void SetUserId(String _userId) {
        this._userId = _userId;
    }
    public String GetUserId() {
        return _userId;
    }
    public void SetUserName(String _userName) {
        this._userName = _userName;
    }
    public String GetUserName() {
        return _userName;
    }
    public void SetUserPassWord(String _userPassWord) {
        this._userPassWord = _userPassWord;
    }
    public String GetUserPassWord() {
        return _userPassWord;
    }
    public void SetUserLogo(byte[] _userLogo) {
        this._userLogo = _userLogo;
    }
    public byte[] GetUserLogo() {
        return _userLogo;
    }

}
