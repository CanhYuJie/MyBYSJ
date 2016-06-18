package cn.lyj.mybysj;

public interface I {
	/** 客户端标识*/
	String CLIENTUSER								=		"ClientUser";
	String KEY_REQUEST 								= 		"request";
	/** 客户端发送的注册请求*/
	String REQUEST_REGISTER		 					= 		"register";
	/** 客户端发送的登陆请求 */
	String REQUEST_LOGIN 							= 		"login";
	/** 客户端新增学生信息的请求*/
	String REQUEST_ADDSTUINFO                       =       "addStuInfo";
	/** 客户端新增楼宇信息的请求*/
	String REQUEST_ADDFLOORINTO                     =		"addFloorInfo";
	/** 客户端修改学生信息的请求*/
	String REQUEST_MODSTUINFO						=		"modStuInfo";
	/** 客户端修改楼宇信息的请求*/
	String REQUEST_MODFLOORINFO						=		"modFloorInfo";
	/** 客户端新增寝室信息的请求*/
	String REQUEST_ADDBEDROOMINFO					=		"addBedRoomInfo";
	/** 客户端清楚日志信息的请求*/
	String REQUEST_CLEANLOG							=		"cleanLog";
	/** 客户端修改密码的请求*/
	String REQUEST_UPDATEPASSWORD					=		"modPassWord";
	/** 客户端获取学生信息的请求*/
	String REQUEST_GETSTUINFO						=		"getStuInfo";
	/** 客户端获取某个学生信息的请求*/
	String REQUEST_GETONSTUINFO						=		"getOneStuInfo";
	/** 客户端添加黑名单的请求*/
	String REQUEST_ADDBLACKLIST						=		"addBlack";
	/** 客户端获取日志信息的请求*/
	String REQUEST_GETLOG							=		"getLog";
	/** 客户端发送的删除某个学生信息的请求*/
	String REQUEST_DELSTUINFO						=		"delStuInfo";
	/** 客户端发送的获取楼宇信息的请求*/
	String REQUEST_GETFLOORINFO						=		"getFloorInfo";
	/** 客户端发送的删除楼宇信息的请求*/
	String REQUEST_DELFLOORINTO						=		"delFloorInfo";
	/** 客户端发送的修改楼宇信息的请求*/
	String REQUEST_MODFLOORINTO						=		"modFloorInfo";
	/** 客户端发送的获取寝室信息的请求*/
	String REQUEST_GETBEDROOMINFO					=		"getBedRoomInfo";
	/** 客户端发送的删除寝室信息的请求*/
	String REQUEST_DELBEDROOMINFO					=		"delBedRoomInfo";
	/** 客户端发送的修改寝室信息的请求*/
	String REQUEST_MODBEDROOMINFO					=		"modBedRoomInfo";
	/** 客户端发送的获取系部列表的请求*/
	String REQUEST_GETDEPARTMENT					=		"getDepartment";
	/** 客户端发送的获取班级列表的请求*/
	String REQUEST_GETCLASS							=		"getClass";
	/** 客户端发送的获取专业列表的请求*/
	String REQUEST_GETMAJORS						=		"getMajors";
	/** 客户端发送的通过寝室简称获取学生列表的请求*/
	String REQUEST_SEASTUBYROOM						=		"seaStuByRoom";
	/** 客户端发送的通过楼宇和区域信息获取寝室列表的请求*/
	String REQUEST_SEABEDROOMBYAREAANDFLOOR			=		"seaBedRoomByAreaAndFloor";
	/** 客户端发送的通过系部信息获取专业列表的请求*/
	String REQUEST_SEAMAJORSBYDEPARTMENT			=		"seaMajorsByDepartment";
	/** 客户端发送的根据专业获取班级列表的请求*/
	String REQUEST_SEACLASSBYMAJOR					=		"seaClassByMajor";
	/** 客户端发送的根据班级获取该班学生信息的请求*/
	String REQUEST_SEASTUBYCLASS					=		"seaStuByClass";

	/** 登录请求参数*/
		public static interface USERLOGIN{
		String USERNAME = "userName";
		String PASSWORD = "passWord";
	}

	/** 注册请求参数*/
	public static interface USERREGISTER{
		String USERNAME = "userName";
		String PASSWORD = "passWord";
	}

	/**  添加学生信息请求参数*/
	public static interface ADDSTUINFO{
		String NAME = "name";
		String UID = "uid";
		String SEX = "sex";
		String BCLASS = "bclass";
		String BDEPARTMENT = "bdepartment";
		String BBEDROOM = "bbedroom";
		String REMARK = "remark";   /** 特别注明：如果使用学生学号登录，remark就是登录密码*/
		String OPTUSER = "optUser";
	}

	/** 添加新楼宇信息*/
	public static interface ADDFLOORINFO{
		String FLOORNAME = "floorName";
		String OPTUSER = "optUser";
	}

	/**  修改学生信息请求参数*/
	public static interface MODSTUINFO{
		String NAME = "name";
		String UID = "uid";
		String SEX = "sex";
		String BCLASS = "bclass";
		String BDEPARTMENT = "bdepartment";
		String BBEDROOM = "bbedroom";
		String REMARK = "remark";   /** 特别注明：如果使用学生学号登录，remark就是登录密码*/
		String OPTUSER = "optUser";
		String MARK = "mark";
	}

	/**  修改楼宇信息请求参数*/
	public static interface MODFLOORINFO{
		String FLOORNAME = "floorName";
		String OPTUSER = "optUser";
		String MARK = "mark";
	}

	/** 添加寝室信息请求参数*/
	public static interface ADDBEDROOMINFO{
		String B_FLOOR = "b_floor";
		String B_AREA = "b_area";
		String INTRO = "intro";
		String OPTUSER = "optUser";
	}

	/** 清楚日志的请求参数*/
	public static interface CLEANLOG{
		String OPTUSER = "optUser";
	}

	/** 修改密码的请求参数*/
	public static interface MODPASSWORD{
		String OPTUSER = "optUser";
		String MARK = "mark";
		String PASSWORD = "passWord";
	}

	/** 获取学生信息的请求参数（所有学生）*/
	public static interface GETSTUINFO{
		String OPTUSER = "optUser";
	}

	/** 获取某个学生的信息的请求参数*/
	public static interface GETONESTUINFO{
		String OPTUSER = "optUser";
		String UID = "uid";
	}

	/** 添加黑名单*/
	public static interface ADDBLACK{
		String PERSIONUID = "persionUid";
		String UID = "uid";
	}

	/** 获取日志信息的请求参数*/
	public static interface GETLOG{
		String OPTUSER = "optUser";
	}


	/** 删除某个学生信息的请求参数*/
	public static interface DELSTUINFO{
		String OPTUSER = "optUser";
		String UID = "uid";
	}

	/** 获取楼宇信息的请求参数*/
	public static interface GETFLOORINFO{
		String OPTUSER = "optUser";
	}

	/** 删除楼宇信息的请求参数*/
	public static interface DELFLOORINFO{
		String OPTUSER = "optUser";
		String FLOOR = "floor";
	}

	/** 获取寝室信息的请求参数*/
	public static interface GETBEDROOMINFO{
		String OPTUSER = "optUser";
	}

	/** 删除寝室信息的请求参数*/
	public static interface DELBEDROOMINFO{
		String OPTUSER = "optUser";
		String INTRO = "intro";
	}
	/** 修改寝室信息的请求参数*/
	public static interface MODBEDROOMINFO{
		String B_FLOOR = "b_floor";
		String B_AREA = "b_area";
		String INTRO = "intro";
		String OPTUSER = "optUser";
		String MARK = "mark";
	}

	/** 获取系部列表信息的请求参数*/
	public static interface GETDEPARTMENT{
		String OPTUSER = "optUser";
	}

	/** 获取班级列表信息的请求参数*/
	public static interface GETCLASS{
		String OPTUSER = "optUser";
	}

	/** 获取专业列表信息的请求参数*/
	public static interface GETMAJORS{
		String OPTUSER = "optUser";
	}

	/** 通过寝室简称获取住寝学生列表的请求参数*/
	public static interface SEASTUBYROOM{
		String OPTUSER = "optUser";
		String ROOMINTRO = "roomIntro";
	}

	/** 根据区域和楼宇获取寝室信息的请求参数*/
	public static interface SEABEDROOMBYAREAANDFLOOR{
		String OPTUSER = "optUser";
		String FLOOR = "floor";
		String AREA = "area";
	}

	/** 根据系部获取专业列表信息的请求参数*/
	public static interface SEAMAJORSBYDEPARTMENT{
		String OPTUSER = "optUser";
		String DEPARTMENT = "department";
	}

	/** 根据专业信息获取班级列表的请求参数*/
	public static interface SEACLASSBYMAJOR{
		String OPTUSER = "optUser";
		String MAJOR = "major";
	}

	/** 根据班级获取学生信息列表的请求参数*/
	public static interface SEASTUBYCLASS{
		String CLASS = "class";
		String OPTUSER = "optUser";
	}

}
