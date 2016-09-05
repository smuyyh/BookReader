package com.justwayward.reader.bean.user;

/**
 * @author yuyh.
 * @date 16/9/5.
 */
public class TencentLoginResult {


    /**
     * ret : 0
     * pay_token : 28DE80BCA3104F9609B394F1EEB60F7x
     * pf : desktop_m_qq-10000144-android-2002-
     * query_authority_cost : 342
     * authority_cost : 0
     * openid : 98AACEE0C75501E20A5C7A8C353041Ax
     * expires_in : 7776000
     * pfkey : a74c189dfd8867ec1e57e76ac2d2c9c6
     * msg :
     * access_token : 28B367EBBE91393A2F1C3AE70A1FCB0F
     * login_cost : 567
     */

    public int ret;
    public String pay_token;
    public String pf;
    public int query_authority_cost;
    public int authority_cost;
    public String openid;
    public int expires_in;
    public String pfkey;
    public String msg;
    public String access_token;
    public int login_cost;

    @Override
    public String toString() {
        return "TencentLoginResult{" +
                "ret=" + ret +
                ", pay_token='" + pay_token + '\'' +
                ", pf='" + pf + '\'' +
                ", query_authority_cost=" + query_authority_cost +
                ", authority_cost=" + authority_cost +
                ", openid='" + openid + '\'' +
                ", expires_in=" + expires_in +
                ", pfkey='" + pfkey + '\'' +
                ", msg='" + msg + '\'' +
                ", access_token='" + access_token + '\'' +
                ", login_cost=" + login_cost +
                '}';
    }
}
