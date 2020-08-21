package com.ailbb.ajj.encrypt;

import com.ailbb.ajj.$;

import java.util.List;

/**
 * Created by Wz on 8/18/2020.
 脱敏规则，为具体的脱敏内容，通过输入来配置，格式为#[位数]*[位数]，
 #代表的是不加密位数，*代表的是要加密的位数，%代表其余所有。
 例如：一个11位的手机号码，全部加密则配置为*[11]或*[%]、前三位不加密，后8位加密则配置为#[3]*[%]或#[3]*[8]。
 又如一个未知长度的字符串，如只加密前8位，其余不加密则配置为*[8]#[%]，长度不满足配置的，当做异常数据处理，不加密。
 实例：
 原值	加密配置	结果
 13452180666	*[11]或*[%]	5741A4CEC1DC465E2C8F61C12ECE881D
 13452180666	#[3]*[%]或#[3]*[8]	134A826021958402B0C4F72554D0EFE2C82
 13452180666	#[3]*[6]#[%]或#[3]*[%]#[2]	13491D3A9D51EE9570B22735457982F23A766
 8642250331272678	#[3]*[%]#[3]	8640EAD0ABB30040D4B6E6E3383F8D5A0B9678
 */
public class EncryptionRule {
    private boolean bad = false; // 解析的规则是否有效
    private String rule; // 规则备份
    private List<String> rules; // 规则集合
    private int dateLength = 0; // 数据长度，长度为0的，自动伸缩截取
    private int blurIndex = 0; // 模糊匹配指针的位置
    private int encodeRuleSumLength = 0; // 加密规则数据的长度
    private int decodeRuleSumLength = 0; // 解密规则数据的长度
    private String[] prefixs; // 加密前缀集合
    private int[][] encodeSubNums; // 加密的数据集
    private int[][] decodeSubNums; // 解密的数据集

    public EncryptionRule(String rule) { this(rule, 0); }

    /**
     * 新建
     * @param rule 规则
     * @param length 数据长度
     */
    public EncryptionRule(String rule, int length) {
        int blurLength = length > 0 ? (length- EncryptUtil.getRuleSumLength(rule)) : 0; // 数据与规则预计的长度差，即需要模糊匹配的长度

        if(ruleTest(length, blurLength < 0)) return;  // 数据长度不满足规则长度（当数据长度为0时，做新建处理）

        this.dateLength = length;
        this.rule = rule; // 规则字符串
        this.rules = EncryptUtil.ruleToList(rule); // 所有规则集
        int ruleSize = rules.size(); // 规则数量

        if(ruleTest(length, ruleSize == 0)) return; // 规则不符合要求

        this.prefixs = new String[ruleSize]; // 前缀集合
        this.encodeSubNums = new int[ruleSize][2]; // 截取的加密数字集合
        this.decodeSubNums = new int[ruleSize][2]; // 截取的解密数字集合

        // 循环解析规则，生成截取字段规则 // #[3] *[2] #[%] *[2] *[%]
        for(int i=0,n=0,d=0; i<ruleSize; i++) {
            String r = rules.get(i); // 获取规则信息
            String prefix = $.regex.regexFirst("(\\#)|(\\*)", r); // 规则前缀
            String num = $.regex.regexFirst("\\d+", r); // 截取数字
            String all = $.regex.regexFirst("\\%|(^\\*$)|(^\\#$)", r); // 模糊匹配字符
            int encodeSubNum = !$.isEmptyOrNull(all) && ((this.blurIndex = i) >= 0) ? blurLength : $.integer.toInt(num); // 截取的字符截止位
            int decodeSubNum = (prefix.equals(EncryptUtil.encryptionPrefix) ? Math.round(blurLength/16+1)*32 : encodeSubNum); // 截取的字符截止位

            this.encodeRuleSumLength += encodeSubNum; // 加密规则字段累加和
            this.decodeRuleSumLength += decodeSubNum; // 解密规则字段累加和

            this.encodeSubNums[i][0] = n; // 开始位
            this.encodeSubNums[i][1] = n += encodeSubNum; // 截止位 如果模糊匹配的话，就是n,n，判断长度来加；

            this.decodeSubNums[i][0] = d; // 解密开始位
            this.decodeSubNums[i][1] = d += decodeSubNum; // 解密开始位

            this.prefixs[i] = prefix; // 加密前缀集合
        }
    }

    public boolean isBad() {
        return bad;
    }

    public void setBad(boolean bad) {
        this.bad = bad;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public List<String> getRules() {
        return rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }

    public int getDateLength() {
        return dateLength;
    }

    public void setDateLength(int dateLength) {
        this.dateLength = dateLength;
    }

    public int getBlurIndex() {
        return blurIndex;
    }

    public void setBlurIndex(int blurIndex) {
        this.blurIndex = blurIndex;
    }

    public int getEncodeRuleSumLength() {
        return encodeRuleSumLength;
    }

    public void setEncodeRuleSumLength(int encodeRuleSumLength) {
        this.encodeRuleSumLength = encodeRuleSumLength;
    }

    public int getDecodeRuleSumLength() {
        return decodeRuleSumLength;
    }

    public void setDecodeRuleSumLength(int decodeRuleSumLength) {
        this.decodeRuleSumLength = decodeRuleSumLength;
    }

    public String[] getPrefixs() {
        return prefixs;
    }

    public void setPrefixs(String[] prefixs) {
        this.prefixs = prefixs;
    }

    public int[][] getEncodeSubNums() {
        return encodeSubNums;
    }

    public void setEncodeSubNums(int[][] encodeSubNums) {
        this.encodeSubNums = encodeSubNums;
    }

    public int[][] getDecodeSubNums() {
        return decodeSubNums;
    }

    public void setDecodeSubNums(int[][] decodeSubNums) {
        this.decodeSubNums = decodeSubNums;
    }

    public int[] getEncodeSubNum(int i) {
        return encodeSubNums[i];
    }

    public int[] getDecodeSubNum(int i) {
        return decodeSubNums[i];
    }

    /**
     * 测试规则
     * @param length
     * @param flags
     * @return
     */
    public boolean ruleTest(int length, boolean... flags){
        boolean error = false; // 初始值是正确的

        for(boolean b : flags)
            if(b) { // 某个规则校验不通过
                error = true; // 初始值设置为false，设置错误的处理方法

                this.encodeRuleSumLength = length;
                this.decodeRuleSumLength = length;

                this.bad = true;
            }

        return error;
    }

    /**
     * 截取加密字段
     * @param data
     * @param index
     * @return
     */
    public String subEncodeData(String data, int index) {
        int[] it = encodeSubNums[index];

        if(dateLength != 0 || index < blurIndex) return data.substring(it[0],it[1]); // 截取关键数据

        int blurLength = data.length() - encodeRuleSumLength; // 进入滑块儿处理
        return data.substring(it[0] + (index == blurIndex ? 0 : blurLength),it[1] + blurLength); // 截取关键数据
    }

    /**
     * 截取解密字段
     * @param data
     * @param index
     * @return
     */
    public String subDecodeData(String data, int index) {
        int[] it = decodeSubNums[index];

        if(dateLength != 0 || index < blurIndex) return data.substring(it[0],it[1]); // 截取关键数据

        int blurLength = data.length() - decodeRuleSumLength; // 进入滑块儿处理
        return data.substring(it[0] + (index == blurIndex ? 0 : blurLength),it[1] + blurLength); // 截取关键数据
    }
}
