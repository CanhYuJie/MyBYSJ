package cn.lyj.mybysj;

public interface I {
	String KEY_REQUEST 								= 		"request";
	/** 上传图片的类型：user_avatar或group_icon */
	String REQUEST_REGISTER		 					= 		"register";
	/**  发送取消注册的请求 */
	String REQUEST_UPDATE_USER_NICK 				= 		"update_nick";
	/** 客户端修改密码的请求 */
	String REQUEST_UPDATE_USER_PASSWORD 			= 		"update_password";
	/** 客户端发送的登陆请求 */
	String REQUEST_LOGIN 							= 		"login";
}
