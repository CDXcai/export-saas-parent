package com.itheima.common.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @Description:
 * @Author:			传智宋江
 * @Company:		http://java.itcast.cn
 * @CreateDate:		2014年12月27日
 */
public class Encrypt {
	/*
	 * 散列算法一般用于生成数据的摘要信息，是一种不可逆的算法，一般适合存储密码之类的数据，
	 * 常见的散列算法如MD5、SHA等。一般进行散列时最好提供一个salt（盐），比如加密密码“admin”，
	 * 产生的散列值是“21232f297a57a5a743894a0e4a801fc3”，
	 * 可以到一些md5解密网站很容易的通过散列值得到密码“admin”，
	 * 即如果直接对密码进行散列相对来说破解更容易，此时我们可以加一些只有系统知道的干扰数据，
	 * 如用户名和ID（即盐）；这样散列的对象是“密码+用户名+ID”，这样生成的散列值相对来说更难破解。
	 */

	/**
	 * md5是一种加密算法 不可逆的加密算法
	 * 特点: 不可逆性,对称性加密,固长性
	 * 1==>> 32位长度
	 * 2==>> 32位长度
	 * 123==>> 32位长度
	 * 123456 ==>> 32位长度
	 *
	 * 123->> xxxxx
	 * 456->> yyyyy
	 * 加盐加密: 每一个密码对应的固定的一个加密的字符串 , 每一个人使用同一个密码 加密的结果是一样
	 * 加盐: 修改原来的用户密码 , 在密码的后面 加上一个盐 加密后的结果 肯定不一致
	 * 每一个人的盐不一样,这个盐对于这个人来说 必须是唯一的,不可修改的 ( 身份证,用户名)
	 * A用户 :  123+4->> qqqqq ->> qwerty
	 * B用户 :  123+5->> wwwww
	 * B用户 :  123+6->> eeeee
	 * 加上md5的运行次数
	 *
	 * @param password
	 * @param salt
	 * @return
	 */
	//高强度加密算法,不可逆
	public static String md5(String password, String salt){
		return new Md5Hash(password,salt,2).toString();
	}

	public static void main(String[] args) {
		System.out.println(new Md5Hash("123456","tony",2).toString());
	}


}
