package com.zxjy.commonlib.config;

import android.content.Context;

import com.zxjy.commonlib.R;
import com.zxjy.commonlib.bean.request_result.BaseConfigModel;
import com.zxjy.commonlib.utils.DecimalUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.IntDef;

public class BaseConfig {

    public static final String BROKERID = "com.zxjy.middleman";
    public static final String CLIENTID = "com.zxjy.client";
    public static final String DRIVERID = "com.zxjy.driver";

    public static final int BROKER_TYPE = 5;
    public static final int CLIENT_TYPE = 1;
    public static final int DRIVER_TYPE = 2;

    public static final String WXBROKER = "wxb10693cdc35b928c";
    public static final String WXDRIVER = "wx1b5fda2851b83685";
    public static final String WXCLIENT = "wxcb0958d7cce17657";

    public static final String WX_MINI_APP_ORIGINAL_ID="gh_c2995e9d89a4";

    public static final String EMPTY_STRING = "";
    public static final String EMPTY_STRING_ONE = " ";

    public static final String PHONE_CODE_FORGET = "FORGET_TIMER";

    public static final String LOGINTIME ="longTime";

    private static final String APK_FILE_KEYWORD = "zxjy";
    public static final String FOLDER_NAME= APK_FILE_KEYWORD +"client";

    public static final int CHANGE_PASSWORD_TYPE_LOGIN = 2;
    public static final int CHANGE_PASSWORD_TYPE_FUND = 3;

    public static final int CHARGE_ADD=1;
    public static final int CHARGE_REDUCE=2;

    public static final int PAYMENT_TYPE_INFORMATION = 1;
    public static final int PAYMENT_TYPE_TRANSFER_FEE = 2;

    // 微信支付时间更新
    public static final int WEI_XIN_PAYMENT_TIMEOUT=5;

    /**
     * 请求订单、运单分类
     */
    public static final int ORDERTYPE_ALL = 0;
    public static final int ORDERTYPE_ACCEPT = 2;

    /**
     * 通知类型
     */
    public static final String NOTIFICATION_TRADE_INFO_CHANNALID = "minTrade";
    public static final String NOTIFICATION_SERVICE_NOTICE_CHANNALID = "serviceNotice";
    public static final String NOTIFICATION_SYS_NOTICE_CHANNALID = "notice";
    public static final String NOTIFICATION_SYS_NOTICE_SERVICE = "service";

    /**
     * 订单信息字段分类标识
     */
    public static final String MIDDLEFIX = "/";
    public static final String SPLITMIDDLEFIX = "/";
    public static final String YUAN_SIGN = "¥";
    public static final String YUAN = "元";
    public static final String FEENOSET = "未设置";
    /**
     * 货物重量体积中间值
     */
    public static final String GOODS_DW_DV_MIDDLE_FIX = "-";

    public static String getYftString(int yft){
        String tag;
        switch (yft){
            case 1:
                tag = "车";
                break;
            case 2:
                tag = "吨";
                break;
            case 3:
                tag = "立方";
                break;
            default:
                tag = "";
                break;
        }
        return tag;
    }

    /**
     * 车辆类型分类
     * @return
     */
    public static List<BaseConfigModel>  getCarTypeList(){
        List<BaseConfigModel> sectionModel = new ArrayList<>();
        sectionModel.add(new BaseConfigModel("平板",1));
        sectionModel.add(new BaseConfigModel("高栏",2));
        sectionModel.add(new BaseConfigModel("中栏",3));
        sectionModel.add(new BaseConfigModel("低栏",4));
        sectionModel.add(new BaseConfigModel("高低板",5));
        sectionModel.add(new BaseConfigModel("厢式",6));
        sectionModel.add(new BaseConfigModel("自卸",7));
        sectionModel.add(new BaseConfigModel("保温",8));
        sectionModel.add(new BaseConfigModel("冷藏",9));
        sectionModel.add(new BaseConfigModel("危险品",10));
        sectionModel.add(new BaseConfigModel("集装箱",11));
        sectionModel.add(new BaseConfigModel("特种",12));
        sectionModel.add(new BaseConfigModel("其他",13));
        return sectionModel;
    }

    // 运输类型
     public static ClassifySectionModel initLoadingType(String headerTitle, int maxSelecteNumber){
    ClassifySectionModel sectionModel = new ClassifySectionModel(headerTitle,1,new ArrayList<>(),ClassifyTypeEnum.LOADINTTYPE);
    sectionModel.getSingleItemModels().add(new ClassifySingleItemModel("一装一卸",1));
    sectionModel.getSingleItemModels().add(new ClassifySingleItemModel("一装两卸",2));
    sectionModel.getSingleItemModels().add(new ClassifySingleItemModel("一装多卸",3));
    sectionModel.getSingleItemModels().add(new ClassifySingleItemModel("两装一卸",4));
    sectionModel.getSingleItemModels().add(new ClassifySingleItemModel("两装两卸",5));
    sectionModel.getSingleItemModels().add(new ClassifySingleItemModel("两装多卸",6));
    return sectionModel;
    }

// 一装一卸(1,"一装一卸"),
// 一装两卸(2,"一装两卸"),
// 两装一卸(3,"两装一卸"),
// 两装两卸(4,"两装两卸"),

    /**
     * 车辆类型分类
     * @return
     */
    public static List<BaseConfigModel>  getCarLongList(){
        List<BaseConfigModel> sectionModel = new ArrayList<>();
        sectionModel.add(new BaseConfigModel("4.2米",1));
        sectionModel.add(new BaseConfigModel("5.0米",2));
        sectionModel.add(new BaseConfigModel("6.2米",3));
        sectionModel.add(new BaseConfigModel("6.8米",4));
        sectionModel.add(new BaseConfigModel("7.2米",5));
        sectionModel.add(new BaseConfigModel("7.7米",6));
        sectionModel.add(new BaseConfigModel("7.8米",7));
        sectionModel.add(new BaseConfigModel("8.2米",8));
        sectionModel.add(new BaseConfigModel("8.7米",9));
        sectionModel.add(new BaseConfigModel("9.6米",10));
        sectionModel.add(new BaseConfigModel("12.5米",11));
        sectionModel.add(new BaseConfigModel("13.0米",12));
        sectionModel.add(new BaseConfigModel("14.6米",13));
        sectionModel.add(new BaseConfigModel("15.0米",14));
        sectionModel.add(new BaseConfigModel("16.5米",15));
        sectionModel.add(new BaseConfigModel("17.5米",16));
        sectionModel.add(new BaseConfigModel("其他",17));
        return sectionModel;
    }

    public static List<String> deliveryLoadingUnLoadingGoodsType(Context context){
        if (context == null)return new ArrayList<>();
        String[] arrsy = context.getResources().getStringArray(R.array.array_delivery_type);
        return Arrays.asList(arrsy);
    }

    public static List<String> deliveryCarLoadingType(Context context){
        if (context == null)return new ArrayList<>();
        String[] arrsy = context.getResources().getStringArray(R.array.car_loading_type);
        return Arrays.asList(arrsy);
    }

    public static String turnMoneyToBaseValue(double money){
        return DecimalUtils.formatTosepara(money,2)+ BaseConfig.YUAN;
    }

    public static String turnMoneyToNoSufixBaseValue(double money){
        return DecimalUtils.formatTosepara(money, 0)+ BaseConfig.YUAN;
    }

    public static String turnMoneyToNoSufixBaseValue(double money,String prefix){
        return prefix + DecimalUtils.formatTosepara(money, 0);
    }

    public static String turnMoneyMineBaseValue(double money){
        return DecimalUtils.formatTosepara(money, 2);
    }



    /**
     * 支付方式
     */
    public static final int PAYTYPE_PLATFORM = 1;
    public static final int PAYTYPE_DELIVERY = 2;
    @IntDef({PAYTYPE_PLATFORM,PAYTYPE_DELIVERY})
    @Retention(RetentionPolicy.SOURCE)
    public  @interface WAYBILLPAYTYPE{
        int value() default 1;
    }
    public static String getPayType(@WAYBILLPAYTYPE int status) {
        if (status == PAYTYPE_PLATFORM){
            return "平台付款";
        }else if (status == PAYTYPE_DELIVERY){
            return "货到付款";
        }else{
            return "";
        }
    }

    public static List<String> paywayType(){
        List<String> type = Arrays.asList("货到付款","平台付款");
        return type;
    }

    /**
     * 修改密码类型
     */

    public static final int  CHANGE_PASSWORD_TYPE_FORGET = 1;
    public static final int CHANGE_PASSWORD_TYPE_UPDATE = 2;
    @IntDef({CHANGE_PASSWORD_TYPE_FORGET,CHANGE_PASSWORD_TYPE_UPDATE})
    @Retention(RetentionPolicy.SOURCE)
    private @interface CHANGEPASSWORDTYPE{
        int value() default 1;
    }

    /**
     * 订单状态
     */
    public static final int ORDERWAITDONE = 1;
    public static final int ORDERDOING = 2;
    public static final int ORDERCOMPLETE = 3;

    @IntDef({ORDERWAITDONE, ORDERDOING, ORDERCOMPLETE})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ORDERSTAUTS{
        int value() default 1;
    }

    @ORDERSTAUTS
    private int status;

    public void setStatus(@ORDERSTAUTS int status) {
        this.status = status;
    }

    public static String getStatus(@ORDERSTAUTS int status) {
        if (status == ORDERWAITDONE){
            return "待接单";
        }else if (status == ORDERDOING){
            return "已接单";
        }else if (status == ORDERCOMPLETE){
            return "已关闭";
        }else{
            return "";
        }
    }

    public enum WayBillStatusEnum {
                WAYBILLOWNERCONFIRM(1,"待客户签约"),WAYBILLDRIVERCONFIRM(2,"待司机签约"),
                WAYBILLFORTHELOADING(3,"待取货"),WAYBILLINTRANSIT(4,"运输中"),
                WAYBILLISTHEUNLOADING(5,"待收货"),WAYBILLWATINGCLOSE(6,"待关闭"),
                WAYBILLCOMPLETED(7,"已完成"),WAYBILLCANCLED(8,"已撤单"),
                WAYBILLBREAKCONTRACTWATING(9,"违约待审核"),WAYBILLBREAKCONTRACT(10,"已违约");

        private int index;
        private String title;

        private WayBillStatusEnum(int index,String title){
            this.index = index;
            this.title = title;
        }

        public int getIndex() {
            return index;
        }

        public String getTitle() {
            return title;
        }
    }

    public static final int WAYBILLOWNERCONFIRM = 1;
    public static final int WAYBILLDRIVERCONFIRM = 2;
    public static final int WAYBILLFORTHELOADING = 3;
    public static final int WAYBILLINTRANSIT = 4;
    public static final int WAYBILLISTHEUNLOADING = 5;
    public static final int WAYBILLWATINGCLOSE = 6;
    public static final int WAYBILLCOMPLETED = 7;
    public static final int WAYBILLCANCLED = 8;
    public static final int WAYBILLBREAKCONTRACTWATING = 9;
    public static final int WAYBILLBREAKCONTRACT = 10;

    @IntDef({WAYBILLOWNERCONFIRM, WAYBILLDRIVERCONFIRM, WAYBILLFORTHELOADING, WAYBILLINTRANSIT, WAYBILLISTHEUNLOADING, WAYBILLWATINGCLOSE, WAYBILLCOMPLETED, WAYBILLCANCLED,WAYBILLBREAKCONTRACTWATING,WAYBILLBREAKCONTRACT})
    @Retention(RetentionPolicy.SOURCE)
    public  @interface WAYBILLSTAUTS{}

    @WAYBILLSTAUTS
    private int wayBillStatus;

    public void setWayBillStatus(@WAYBILLSTAUTS int wayBillStatus) {
        this.wayBillStatus = wayBillStatus;
    }

    public static String getWayBillStatus(@WAYBILLSTAUTS int wayBillStatus){
        if (wayBillStatus == WAYBILLOWNERCONFIRM){
            return "待客户签约";
        }else if (wayBillStatus == WAYBILLDRIVERCONFIRM){
            return "待司机签约";
        }else if (wayBillStatus == WAYBILLFORTHELOADING){
            return "待取货";
        }else if (wayBillStatus == WAYBILLINTRANSIT){
            return "运输中";
        }else if (wayBillStatus == WAYBILLISTHEUNLOADING){
            return "待收货";
        }else if (wayBillStatus == WAYBILLWATINGCLOSE){
            return "待关闭";
        }else if (wayBillStatus == WAYBILLCOMPLETED){
            return "已完成";
        }else if (wayBillStatus == WAYBILLCANCLED){
            return "已撤单";
        }else if (wayBillStatus == WAYBILLBREAKCONTRACTWATING){
            return "违约待审核";
        }else if (wayBillStatus == WAYBILLBREAKCONTRACT){
            return "已违约";
        }else{
            return "";
        }
    }

    public static String getWayBIllStatusButtonTitle(@WAYBILLSTAUTS  int wayBillStatus){
        if (wayBillStatus == WAYBILLFORTHELOADING){
            return "取货";
        }else if (wayBillStatus == WAYBILLINTRANSIT){
            return "卸货";
        }else{
            return "";
        }
    }

    /**
     * 需要回单或 开票
     */
    public static final int NEEDHDORIN = 1;
    /**
     * 不需要回单或开票
     */
    public static final int INNEEDHDORIN = 0;

    /**
     * 司机资质审核图片上传
     */
    public static final int USERIMAGE = 1;
    public static final int IDCARDUP = 2;
    public static final int IDCARDDOWN = 3;
    public static final int IDCARDUSERHOLD = 4;
    public static final int USERDRIVERCARD = 5;
    public static final int USERDRIVERNAME = 6;
    public static final int USERDRIVERIDNO = 7;
    public static final int USERDRIVERVALIDETIME = 8;
    public static final int USERIDCARDNO = 9;

    public static final int USERCOMPANYNAME = 10;
    public static final int USERCOMPANYADDRESS = 11;
    public static final int USERCOMPANYADETAILDDRESS = 12;
    public static final int USERBUSSINESSLICENSE = 13;

    public static final int USERCOMPANYID = 14;
    public static final int USERCOMPANYPHONE = 15;
    public static final int COMPANY_BANKCAR_LICENSE = 16;


    @IntDef({USERIMAGE,IDCARDUP,IDCARDDOWN,IDCARDUSERHOLD,USERDRIVERCARD,USERBUSSINESSLICENSE,COMPANY_BANKCAR_LICENSE})
    @Retention(RetentionPolicy.SOURCE)
    public  @interface DRIVERQUALIFICATIONSTAUTS{}


    public static final int IMAGETYPE_GOODS = 1;
    public static final int IMAGETYPE_LOADING = 2;
    public static final int IMAGETYPE_UNLOADING = 3;
    public static final int IMAGETYPE_HUIDAN = 4;
    public static final int IMAGETYPE_IDCARD = 5;
    public static final int IMAGETYPE_DLICENCE = 6;
    public static final int IMAGETYPE_DLICENCEING = 7;
    public static final int IMAGETYPE_CAR = 8;

    @IntDef({IMAGETYPE_GOODS,IMAGETYPE_LOADING,IMAGETYPE_UNLOADING,IMAGETYPE_HUIDAN,IMAGETYPE_IDCARD,IMAGETYPE_DLICENCE,IMAGETYPE_DLICENCEING,IMAGETYPE_CAR})
    @Retention(RetentionPolicy.SOURCE)
    public  @interface IMAGEUPLOADINGTYPE{}

    /**
     * 车辆归属 1 平台车 2 外协车
     */
    public static final int CAR_COMBINATION_PLATFORM = 1;
    public static final int CAR_COMBINATION_OUTSIDE = 2;

    @IntDef({CAR_COMBINATION_PLATFORM,CAR_COMBINATION_OUTSIDE})
    @Retention(RetentionPolicy.SOURCE)
    public  @interface CARCOMBINATIONTYPE{}


    /**
     * 日志操作类型
     */
    public static final int ORDER_OPERATION_TYPE_JYY_CREATE = 1;
    public static final int ORDER_OPERATION_TYPE_YWY_ACCEPT = 2;
    public static final int ORDER_OPERATION_TYPE_YWY_COMMITE = 3;
    public static final int ORDER_OPERATION_TYPE_JJR_TIMEOUT_TRANSFER = 4;
    public static final int ORDER_OPERATION_TYPE_YWY_DENY_ORDER = 5;
    public static final int ORDER_OPERATION_TYPE_JJR_TRANSFER = 6;
    public static final int ORDER_OPERATION_TYPE_CANCLE_ORDER = 7;
    public static final int ORDER_OPERATION_TYPE_UPDATE_ORDER = 8;
    public static final int ORDER_OPERATION_TYPE_ADD_PLAIN = 9;
    public static final int ORDER_OPERATION_TYPE_DELETE_PLAIN = 10;
    public static final int ORDER_OPERATION_TYPE_DDY_ACCEPT = 11;
    public static final int ORDER_OPERATION_TYPE_DDY_DENY = 12;
    public static final int ORDER_OPERATION_TYPE_DDY_TRANSFER = 13;
    public static final int ORDER_OPERATION_TYPE_DDY_ADD_PRICE = 14;
    public static final int ORDER_OPERATION_TYPE_DDY_DELETE_PRICE = 15;
    public static final int ORDER_OPERATION_TYPE_CLOSE_ORDER = 16;
    public static final int ORDER_OPERATION_TYPE_TRANSER_ORDER = 17;
    public static final int ORDER_OPERATION_TYPE_GOODS_IMAGE = 18;
    public static final int ORDER_OPERATION_TYPE_MOVE_SERVICE = 19;

    public static final int ORDER_OPERATION_TYPE_DDY_SEND_CAR = 50;
    public static final int ORDER_OPERATION_TYPE_JYY_YWY_SINGED = 51;
    public static final int ORDER_OPERATION_TYPE_SJ_DDY_SIGNED = 52;
    public static final int ORDER_OPERATION_TYPE_CONFIRM_LOAD_GOODS = 53;
    public static final int ORDER_OPERATION_TYPE_CONFIRM_UNLOAD_GOODS = 54;
    public static final int ORDER_OPERATION_TYPE_CONFIRM_GOODS_SENDED = 55;
    public static final int ORDER_OPERATION_TYPE_DDY_PAY_FEE = 56;
    public static final int ORDER_OPERATION_TYPE_CLOSE_YD = 57;
    public static final int ORDER_OPERATION_TYPE_CANCLE_YD = 58;
    public static final int ORDER_OPERATION_TYPE_DDY_SEND_CAR_AGAIN = 59;
    public static final int ORDER_OPERATION_TYPE_HZ_PAY_FEE = 60;
    public static final int ORDER_OPERATION_TYPE_HZ_SIGNED = 61;
    public static final int ORDER_OPERATION_TYPE_SIJI_SIGNED = 62;
    public static final int ORDER_OPERATION_TYPE_PAY_APPLY = 63;
    public static final int ORDER_OPERATION_TYPE_UPLOAD_IMG = 64;
    public static final int ORDER_OPERATION_TYPE_BREAK_CONTRACT_APPLY = 65;
    public static final int ORDER_OPERATION_TYPE_BREAK_CONTRACT_CONFIRM = 66;
    public static final int ORDER_OPERATION_TYPE_WAYBILL_REFUND_CHARGE = 67;
    public static final int ORDER_OPERATION_TYPE_WAYBILL_REFUND_FREIGHT  = 68;
    public static final int ORDER_OPERATION_TYPE_WAYBILL_ADD_FREIGHT = 69;
    public static final int ORDER_OPERATION_TYPE_WAYBILL_REDUCE_FREIGHT = 70;
    public static final int ORDER_OPERATION_TYPE_WAYBILL_UPDATE_INFO = 71;


    public @interface ORDERLOGOPERATIONTYPE{}

    public static String getOperationLogDc(@ORDERLOGOPERATIONTYPE int operationStatus){
        if (operationStatus == ORDER_OPERATION_TYPE_JYY_CREATE){
            return "交易员创建订单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_YWY_ACCEPT){
            return "业务员受理订单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_YWY_COMMITE){
            return "业务员提交订单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_JJR_TIMEOUT_TRANSFER){
            return "经纪人超时转单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_YWY_DENY_ORDER){
            return "业务员退单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_JJR_TRANSFER){
            return "经纪人主动转单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_CANCLE_ORDER){
            return "撤销订单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_UPDATE_ORDER){
            return "修改订单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_ADD_PLAIN){
            return "添加计划";
        }else if (operationStatus == ORDER_OPERATION_TYPE_DELETE_PLAIN){
            return "删除计划";
        }else if (operationStatus == ORDER_OPERATION_TYPE_DDY_ACCEPT){
            return "调度员接单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_DDY_DENY){
            return "调度员退单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_DDY_TRANSFER){
            return "调度员转单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_DDY_ADD_PRICE){
            return "调度员添加询价单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_DDY_DELETE_PRICE){
            return "调度员撤销询价单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_CLOSE_ORDER){
            return "关闭订单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_TRANSER_ORDER){
            return "平台调度";
        } else if (operationStatus == ORDER_OPERATION_TYPE_GOODS_IMAGE){
            return "上传照片";
        } else if (operationStatus == ORDER_OPERATION_TYPE_DDY_SEND_CAR){
            return "调度员派车";
        }else if (operationStatus == ORDER_OPERATION_TYPE_JYY_YWY_SINGED){
            return "货主/客服签约运单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_SJ_DDY_SIGNED){
            return "司机/调度员签约运单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_CONFIRM_LOAD_GOODS){
            return "确认装货";
        }else if (operationStatus == ORDER_OPERATION_TYPE_CONFIRM_UNLOAD_GOODS){
            return "确认卸货";
        }else if (operationStatus == ORDER_OPERATION_TYPE_CONFIRM_GOODS_SENDED){
            return "确认收货";
        }else if (operationStatus == ORDER_OPERATION_TYPE_DDY_PAY_FEE){
            return "调度员(司机)支付运费";
        }else if (operationStatus == ORDER_OPERATION_TYPE_CLOSE_YD){
            return "关闭运单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_CANCLE_YD){
            return "撤销运单";
        }else if (operationStatus == ORDER_OPERATION_TYPE_DDY_SEND_CAR_AGAIN){
            return "调度员重新派车";
        }else if (operationStatus == ORDER_OPERATION_TYPE_HZ_PAY_FEE){
            return "货主/客服支付运费";
        }else if (operationStatus == ORDER_OPERATION_TYPE_HZ_SIGNED){
            return "货主签约完成";
        }else if (operationStatus == ORDER_OPERATION_TYPE_SIJI_SIGNED){
            return "司机/调度员签约完成";
        }else if (operationStatus == ORDER_OPERATION_TYPE_PAY_APPLY){
            return "支付申请";
        }else if (operationStatus == ORDER_OPERATION_TYPE_UPLOAD_IMG){
            return "上传图片";
        }else if (operationStatus == ORDER_OPERATION_TYPE_BREAK_CONTRACT_APPLY){
            return "违约申请";
        }else if (operationStatus == ORDER_OPERATION_TYPE_BREAK_CONTRACT_CONFIRM){
            return "违约审核";
        }else if (operationStatus == ORDER_OPERATION_TYPE_WAYBILL_REFUND_CHARGE){
            return "服务费退款";
        }else if (operationStatus == ORDER_OPERATION_TYPE_WAYBILL_REFUND_FREIGHT){
            return "运费退款";
        }else if (operationStatus == ORDER_OPERATION_TYPE_WAYBILL_ADD_FREIGHT){
            return "追加运费";
        }else if (operationStatus == ORDER_OPERATION_TYPE_WAYBILL_REDUCE_FREIGHT){
            return "减少运费";
        }else if (operationStatus == ORDER_OPERATION_TYPE_WAYBILL_UPDATE_INFO){
            return "修改运单信息";
        }else if (operationStatus == ORDER_OPERATION_TYPE_MOVE_SERVICE){
            return "客服转单";
        }
        else{
            return "";
        }
    }


    /**
     * 资金流水类型
     */
    public static final int FUND_FLOW_TYPE_IN = 1;
    public static final int FUND_FLOW_TYPE_IA = 2;
    public static final int FUND_FLOW_TYPE_OUT = 3;
    public static final int FUND_FLOW_TYPE_OA = 4;
    public static final int FUND_FLOW_TYPE_IFLUSH = 5;
    public static final int FUND_FLOW_TYPE_OFLUSH = 6;
    public static final int FUND_FLOW_TYPE_CHG_M = 10;
    public static final int FUND_FLOW_TYPE_CHG_U = 11;
    public static final int FUND_FLOW_TYPE_CHG_B = 12;
    public static final int FUND_FLOW_TYPE_CHG = 13;
    public static final int FUND_FLOW_TYPE_CHG_G = 14;
    public static final int FUND_FLOW_TYPE_CMU = 15;
    public static final int FUND_FLOW_TYPE_CMR = 16;
    public static final int FUND_FLOW_TYPE_FRG_U = 17;
    public static final int FUND_FLOW_TYPE_FRG_B = 18;
    public static final int FUND_FLOW_TYPE_FRG = 21;
    public static final int FUND_FLOW_TYPE_FRG_G = 22;
    public static final int FUND_FLOW_TYPE_PNT = 23;
    public static final int FUND_FLOW_TYPE_PNT_G = 24;
    public static final int FUND_FLOW_TYPE_TAX = 25;
    public static final int FUND_FLOW_TYPE_TAX_B = 26;
    public static final int FUND_FLOW_TYPE_TAX_P = 27;
    public static final int FUND_FLOW_TYPE_TAX_G = 28;
    public static final int FUND_FLOW_TYPE_IO_CHAGE = 29;
    public static final int FUND_FLOW_TYPE_CHG_TR_PD  = 30;
    public static final int FUND_FLOW_TYPE_CHG_TR_MK = 31;

    public static final int FUND_FLOW_TYPE_IO_CHANGE_G = 32;
    public static final int FUND_FLOW_TYPE_CHG_TR_PD_IN = 33;
    public static final int FUND_FLOW_TYPE_RF_FRG_OUT= 35;
    public static final int FUND_FLOW_TYPE_RF_FRG_IN = 36;
    public static final int FUND_FLOW_TYPE_RF_TAX_OUT = 37;
    public static final int FUND_FLOW_TYPE_RF_TAX_IN= 38;

    public @interface FUNDFLOWTYPE{}

    public static String getFlowType(@FUNDFLOWTYPE int flowType){
        if (flowType == FUND_FLOW_TYPE_IN){
            return "入帐";
        }else if (flowType == FUND_FLOW_TYPE_IA){
            return "入帐调整";
        }else if (flowType == FUND_FLOW_TYPE_OUT){
            return "出账";
        }else if (flowType == FUND_FLOW_TYPE_OA){
            return "出账调整";
        }else if (flowType == FUND_FLOW_TYPE_IFLUSH){
            return "入帐冲正";
        }else if (flowType == FUND_FLOW_TYPE_OFLUSH){
            return "出账冲正";
        }else if (flowType == FUND_FLOW_TYPE_CHG_M){
            return "平台手续费留存";
        }else if (flowType == FUND_FLOW_TYPE_CHG_U){
            return "服务费支付";
        }else if (flowType == FUND_FLOW_TYPE_CHG_B){
            return "服务费返还";
        }else if (flowType == FUND_FLOW_TYPE_CHG){
            return "服务费支出";
        }else if (flowType == FUND_FLOW_TYPE_CHG_G){
            return "服务费收入";
        }else if (flowType == FUND_FLOW_TYPE_CMU){
            return "服务费支付";
        }else if (flowType == FUND_FLOW_TYPE_CMR){
            return "服务费返还";
        }else if (flowType == FUND_FLOW_TYPE_FRG_U){
            return "违约金支出";
        }else if (flowType == FUND_FLOW_TYPE_FRG_B){
            return "违约金收入";
        }else if (flowType == FUND_FLOW_TYPE_FRG){
            return "运费支付";
        }else if (flowType == FUND_FLOW_TYPE_FRG_G){
            return "运费返还";
        }else if (flowType == FUND_FLOW_TYPE_PNT){
            return "运费支出";
        }else if (flowType == FUND_FLOW_TYPE_PNT_G){
            return "运费收入";
        }else if (flowType == FUND_FLOW_TYPE_TAX){
            return "税费支付";
        }else if (flowType == FUND_FLOW_TYPE_TAX_B){
            return "税费返还";
        }else if (flowType == FUND_FLOW_TYPE_TAX_P){
            return "税费支出";
        }else if (flowType == FUND_FLOW_TYPE_TAX_G){
            return "税费收入";
        }else if (flowType == FUND_FLOW_TYPE_IO_CHAGE){
            return "银行服务费";
        }else if (flowType == FUND_FLOW_TYPE_CHG_TR_PD){
            return "用户资金划转";
        }else if (flowType == FUND_FLOW_TYPE_CHG_TR_MK){
            return "平台资金划转";
        }else if (flowType == FUND_FLOW_TYPE_IO_CHANGE_G){
            return "银行服务费收取";
        }else if (flowType == FUND_FLOW_TYPE_CHG_TR_PD_IN){
            return "用户收款";
        }else if (flowType == FUND_FLOW_TYPE_RF_FRG_OUT){
            return "运费支出退款";
        }else if (flowType == FUND_FLOW_TYPE_RF_FRG_IN){
            return "运费收入退款";
        }else if (flowType == FUND_FLOW_TYPE_RF_TAX_OUT){
            return "税费支出退款";
        }else if (flowType == FUND_FLOW_TYPE_RF_TAX_IN){
            return "税费收入退款";
        }else{
            return "";
        }
    }

    public static final int QUALIFICATION_STATUS_TYEP_NONE = 0;
    public static final int QUALIFICATION_STATUS_TYEP_WATTING = 1;
    public static final int QUALIFICATION_STATUS_TYEP_PASS = 2;
    public static final int QUALIFICATION_STATUS_TYEP_FORBIDEN = 3;
    @IntDef({QUALIFICATION_STATUS_TYEP_NONE,QUALIFICATION_STATUS_TYEP_WATTING,QUALIFICATION_STATUS_TYEP_PASS,QUALIFICATION_STATUS_TYEP_FORBIDEN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface QUALIFICATIONSTATUS{}

    public static String getQualificationStatus(@QUALIFICATIONSTATUS int flowType){
        if (flowType == QUALIFICATION_STATUS_TYEP_NONE){
            return "未认证";
        }else if (flowType == QUALIFICATION_STATUS_TYEP_WATTING){
            return "未审核";
        }else if (flowType == QUALIFICATION_STATUS_TYEP_PASS){
            return "审核通过";
        }else if (flowType == QUALIFICATION_STATUS_TYEP_FORBIDEN){
            return "审核未通过";
        }else{
            return "";
        }
    }

    public static String getCarLong(Context context,int cl){
        try {
            String[] array = context.getResources().getStringArray(R.array.array_car_long);
            return array[cl - 1];
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static String getCarType(Context context,int ct){
        try {
            String[] array = context.getResources().getStringArray(R.array.array_car_type);
            return array[ct - 1];
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
