/// <reference path="/Scripts/jquery-1.8.0.min.js" />
/// <reference path="/Scripts/jquery.validate.min.js" />
$(document).ready(function () {
    // IPv4地址验证
    jQuery.validator.addMethod("IPv4", function (value, element) {
        return this.optional(element) || /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/.test(value);
    }, "请输入标准IPv4地址如127.0.0.1或192.168.1.1");

    // 电话号码验证
    jQuery.validator.addMethod("Telphone", function (value, element) {
        return this.optional(element) || /^\d{3,4}-\d{6,9}(\(\d{6,9}\))*$/.test(value) || /^1\d{10}$/.test(value);
    }, "请输入标准的电话号码如028-8567667或138****9010");

    //手机号码验证
    jQuery.validator.addMethod("Mobile", function (value, element) {
        return this.optional(element) || /^1\d{10}$/.test(value);
    }, "请输入标准的电话号码如138****9010");

    //传真号验证
    jQuery.validator.addMethod("Fax", function (value, element) {
        return this.optional(element) || /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/.test(value);
    }, "请输入标准的传真号码如123 -999 999");
    //可以匹配的字符串如：+123 -999 999 ； +123-999 999 ；123 999 999 ；+123 999999等

    // 标准货币验证
    jQuery.validator.addMethod("Money", function (value, element) {
        return this.optional(element) || /^\d{0,8}(\.\d{2})?$/.test(value);
    }, "请输入标准货币值，8位整数或者8位整数加2位小数");

    // 标准邮编验证
    jQuery.validator.addMethod("Zipcode", function (value, element) {
        return this.optional(element) || /^\d{6}$/.test(value);
    }, "请输入标准的邮编如610016");

    // 十进制数验证
    jQuery.validator.addMethod("Decimal", function (value, element) {
        return this.optional(element) || /^(-)?\d+(\.\d+)?$/.test(value);
    }, "请输入标准的十进制数如");

    // 特殊字符验证
    jQuery.validator.addMethod("Word", function (value, element) {
        return this.optional(element) || /^\w*$/.test(value);
    }, "不能输入除字母、数字和下划线以外的字符");

    // 日期相等判断
    jQuery.validator.addMethod("MaxDate", function (value, element, param) {
        return this.optional(element) || (Date.parse(value.replace(/-/g, "/")) > Date.parse($(param).val().replace(/-/g, "/")));
    }, "当前日期要大于前面日期.");

    // 大于当前时间
    jQuery.validator.addMethod("GreaterDateNow", function (value, element) {
        var myDate = new Date();
        var month = parseInt(myDate.getMonth()) + 1;
        var nowDate = myDate.getFullYear() + "-" + month + "-" + myDate.getDate();
        var nowTime = nowDate + " " + myDate.getHours() + ":" + myDate.getMinutes() + ":" + myDate.getSeconds();
        return this.optional(element) || (Date.parse(value.replace(/-/g, "/")) > Date.parse(nowTime.replace(/-/g, "/")));
    }, "开始时间要大于当前时间.");

    // 数字大于判断
    jQuery.validator.addMethod("MaxNumber", function (value, element, param) {
        return this.optional(element) || (parseInt(value) >= parseInt($(param).val()));
    }, "当前数字要大于或等于前面数字");

    // 字符串相同判断
    jQuery.validator.addMethod("equalTo", function (value, element, param) {
        return this.optional(element) || value == $(param).val();
    }, "当前字符串要和前面的字符串相同");
    //输入格式判断（3,5）
    jQuery.validator.addMethod("inputformat", function (value, element, param) {
        return this.optional(element) || /^\d(\,\d)*/.test(value);
    }, "输入格式如：3,5多节点用逗号分隔 ");
    //QQ验证
    jQuery.validator.addMethod("QQ", function (value, element) {
        return this.optional(element) || /^[1-9]([0-9]){4,11}$/.test(value);
    }, "请输入正确的QQ号");
    //QQ验证
    jQuery.validator.addMethod("WeChat", function (value, element) {
        return this.optional(element) || /^[1-9]([0-9]){4,11}$/.test(value) || /^1\d{10}$/.test(value) || /^[A-Za-z]([0-9A-Za-z_-]){5,19}$/.test(value);
    }, "请输入正确的微信号");
    //邮箱验证
    jQuery.validator.addMethod("Email", function (value, element) {
        return this.optional(element) || /^([a-zA-Z0-9_-]){1,}@([a-zA-Z0-9_-]){1,}((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value);
    }, "邮箱合适应为123@sina.com");
    //联系方式验证
    jQuery.validator.addMethod("ContactWay", function (value, element) {
        return this.optional(element) || /^([a-zA-Z0-9_-]){1,}@([a-zA-Z0-9_-]){1,}((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value) || /^\d{3,4}-\d{6,9}(\(\d{6,9}\))*$/.test(value) || /^1\d{10}$/.test(value);
    }, "请输入正确的联系方式");
    //编码验证
    jQuery.validator.addMethod("Code", function (value, element) {
        return this.optional(element) || /^([a-zA-Z0-9_-]){6,20}$/.test(value);
    }, "请输入正确的编码");
    //编码验证
    jQuery.validator.addMethod("Username", function (value, element) {
        return this.optional(element) || /^([a-zA-Z0-9_-]){6,20}$/.test(value);
    }, "请输入正确的编码");
    // 整数验证
    jQuery.validator.addMethod("Integer", function (value, element) {
        return this.optional(element) || /^[1-9]([0-9]){0,9}$/.test(value);
    }, "请输入标准的1-10位整数");
    //自定义编号验证
    jQuery.validator.addMethod("CustomCode", function (value, element) {
        return this.optional(element) || /^([0-9A-Za-z]){6,20}$/.test(value);
    }, "请输入标准的自定义编号");
    //折扣验证
    jQuery.validator.addMethod("Discount", function (value, element) {
        return this.optional(element) || /^[1-9](.[0-9]{1}){0,1}$/.test(value);
    }, "请输入正确的折扣");
    //房间面积
    jQuery.validator.addMethod("RoomArea", function (value, element) {
        return this.optional(element) || /^[1-9]([0-9]){0,2}$/.test(value) || /^[1-9]([0-9]){0,2}-[1-9]([0-9]){0,2}$/.test(value);
    }, "请输入正确的房间面积");
    // 年龄验证
    jQuery.validator.addMethod("Age", function (value, element) {
        return this.optional(element) || /^[1-9][0-9]$/.test(value);
    }, "请输入正确的年龄");
    // 商品数量验证
    jQuery.validator.addMethod("Quantity", function (value, element) {
        return this.optional(element) || /^[1-9]([0-9]){0,7}$/.test(value) || /^0$/.test(value);
    }, "请输入标准的1-10位整数");
    // 链接地址验证
    jQuery.validator.addMethod("Url", function (value, element) {
        return this.optional(element) || /http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/.test(value);
    }, "请输入标准的1-10位整数");
    //密码验证
    jQuery.validator.addMethod("Password", function (value, element) {
        var myreg = /^[^\s]{6,18}$/;
        if (value != '') { if (!myreg.test(value)) { return false; } };
        return true;
    }, "密码必须为6～18位数字+字母");
    //身份证验证(简单验证)
    jQuery.validator.addMethod("Identity", function (value, element) {
        return this.optional(element) || /(^\d{15}$)|(^\d{17}([0-9]|X|x)$)/.test(value);
    }, "身份证号码必须为15为或者18位，18位号码的最后一位可以为数字或者X");
    // 积分验证
    jQuery.validator.addMethod("Point", function (value, element) {
        return this.optional(element) || /^[1-9]{1}||$/.test(value) || /^10$/.test(value);
    }, "积分量为1-10分");
});