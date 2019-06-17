package com.hmx.utils.common;

import java.util.regex.Pattern;

/**
 * Created by songjinbao on 2019/5/13.
 */
public class CommonUtils {

    public static void main(String[] arg0){
//        String buffer = "<p id=\\\"content\\\">\\n\\t<br />\\n</p>\\n<p>\\n\\t<br />\\n<img src=\\\"http://www.huangmei.gov.cn/upload_file/image/201812/20181219111724_72734.jpg\\\" alt=\\\"\\\" /> \\n</p>\\n<p>\\n\\t图说：黄梅戏《玉天仙》 祖忠人 摄\\n</p>\\n<p>\\n\\t<br />\\n</p>\\n<p>\\n\\t&emsp;&emsp;若是理念不先锋，则“小剧场”无意义。作为2018第四届上海小剧场戏曲节参演剧目，根据《汉书·朱买臣传》改编、安庆市黄梅戏艺术剧院创排的黄梅戏《玉天仙》掀起一阵热议。循着最初《汉书》中这一故事的蛛丝马迹，转换立场和视角，“朱买臣休妻”的故事引发崭新思考。\\n</p>\\n<p>\\n\\t<br />\\n</p>\\n<p>\\n\\t&emsp;&emsp;小剧场戏曲的概念源自小剧场戏剧，而小剧场戏剧特指站在戏剧探索前沿，拥有先锋理念和实验性质的作品，而并非仅仅是在小剧场上演的小作品。应该说，中国戏曲本身就是带有“小剧场”基因的，舞台上简单的一桌二椅，写意化的表演可以描画万水千山，也可以演绎历史兴衰。可是随着时代的变迁和戏曲所遭遇的困境，创新的脚步似乎卡在了某一个点。\\n</p>\\n<p>\\n\\t<br />\\n</p>\\n<p>\\n\\t<img src=\\\"http://www.huangmei.gov.cn/upload_file/image/201812/20181219111852_18080.jpg\\\" alt=\\\"\\\" /> \\n</p>\\n<p>\\n\\t图说：舞台上的黄新德（左）与夏圆圆（祖忠人摄）\\n</p>\\n<p>\\n\\t<br />\\n</p>\\n<p>\\n\\t&emsp;&emsp;或许是不够自信，也或许是太在乎为数不多的老戏迷，舞台上所看到的创新都只停留在表层，弄点声光电影的高科技，搞些服装道具上的小花样，统统都称之为“创新”还忙不迭地解释这是“移步不换形”。快70岁的黄新德说：“我愿意来演《玉天仙》就是想参与到年轻人的’实验’中，找回前辈们在小剧场里争取观众的那份斗志。”\\n</p>\\n<p>\\n\\t<br />\\n</p>\\n<p>\\n\\t&emsp;&emsp;作为黄梅戏首屈一指的表演艺术家，黄新德心痛的是：“我们的戏曲正在逐步失守，从表现手段、制作过程、传承模式等等，都在被蚕食。变得话剧化，歌剧化，娱乐化，杂技化，唯独找不到我们自己。”戏曲要与时俱进，就是要站在传统的基础上，尽可能吸收现代的东西，注入现代精神。\\n</p>\\n<p>\\n\\t<br />\\n</p>\\n<p>\\n\\t<img src=\\\"http://www.huangmei.gov.cn/upload_file/image/201812/20181219111926_75676.jpg\\\" alt=\\\"\\\" /> \\n</p>\\n<p>\\n\\t图说：黄梅戏《玉天仙》剧照 祖忠人 摄\\n</p>\\n<p>\\n\\t<br />\\n</p>\\n<p>\\n\\t&emsp;&emsp;前不久，作为第三届韩国戏剧节中国唯一受邀剧目，黄梅戏《玉天仙》在首尔演出并拿下最佳国际剧目奖。观众席里，不少观众居然看出了眼泪，虽说是完全陌生的艺术形式，故事情节也只能依靠字幕跟进，但并不影响“人妻”们的感同身受，唱腔和表演的美也同样打动他们。\\n</p>\\n<p>\\n\\t<br />\\n</p>\\n<p>\\n\\t<img src=\\\"http://www.huangmei.gov.cn/upload_file/image/201812/20181219111942_64849.jpg\\\" alt=\\\"\\\" /> \\n</p>\\n<p>\\n\\t图说：舞台上的黄新德（右）与夏圆圆（祖忠人摄）\\n</p>\\n<p>\\n\\t<br />\\n</p>\\n<p>\\n\\t&emsp;&emsp;80后的夏圆圆虽年轻，但对自身所饰演的玉天仙体悟很深，“贫穷夫妻百事哀”的生活以及对丈夫“怒其不争”的丰富情感，被融入唱念和表演中，入木三分、层层分明。尤其是最后，当被丈夫“重金赎回”后，想死都不能的心伤和绝望，被展现得淋漓尽致。在她看来，最重要的是，观众坐在剧场里，会去思考这个2000年前的女人所做出的选择，也会激烈探讨旧式婚姻和两性关系。\\n</p>\\n<p>\\n\\t<br />\\n</p>\\n<p>\\n\\t&emsp;&emsp;编剧余青峰说：这种“热议”本身就是成功，在这个信息太过丰富的时代，唯有带起“话题”才能引发关注。喜欢或不喜欢，为什么喜欢、为什么不喜欢，话题意味着流量，流量直接对标的就是关注度。有了这种关注度，才会有更多人愿意去进一步了解这个剧种、这份艺术，有兴趣去欣赏它从表演到唱腔的各种美。\\n</p>";
//        System.out.println(clearNotChinese(buffer));

        String str = "第四届上海小剧场戏曲节参演剧目根据汉书朱买臣传改编安庆市黄梅戏艺术剧院创排的黄梅戏玉天";

        Integer num = str.indexOf("黄梅戏玉");
        System.out.println("num=" + num);
        String count = str.substring((num-5),(num + 9));
        System.out.println("count=" + count);
    }

    /**
     * 去掉字符串中的非汉字字符
     * @param buff
     * @return
     */
    public static String clearNotChinese(String buff){

        String tmpString =buff.replaceAll("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]", "");//去掉所有中英文符号

        char[] carr = tmpString.toCharArray();

        for(int i = 0; i<tmpString.length();i++){

            if(carr[i] < 0xFF){

                carr[i] = ' ' ;//过滤掉非汉字内容

            }

        }

        return String.copyValueOf(carr).trim();

    }

    /*方法二：推荐，速度最快
  * 判断是否为整数
  * @param str 传入的字符串
  * @return 是整数返回true,否则返回false
*/

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
