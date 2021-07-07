package com.ailbb.ajj.unit;

import com.ailbb.ajj.$;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Created by Wz on 12/24/2019.
 */
public class $Coding {

    public static void main(String[] args) {
        String str = "#coding=utf-8\n" +
                "from pyspark import SparkConf, SparkContext\n" +
                "\n" +
                "# 创建SparkConf和SparkContext\n" +
                "conf = SparkConf().setMaster(\"local\").setAppName(\"lichao-wordcount\")\n" +
                "conf.set(\"spark.authenticate.secret\",\"wordcount\")\n" +
                "sc = SparkContext(conf=conf)\n" +
                "\n" +
                "# 输入的数据\n" +
                "data=[\"hello\",\"world\",\"hello\",\"word\",\"count\",\"count\",\"hello\"]\n" +
                "\n" +
                "# 将Collection的data转化为spark中的rdd并进行操作\n" +
                "rdd=sc.parallelize(data)\n" +
                "resultRdd = rdd.map(lambda word: (word,1)).reduceByKey(lambda a,b:a+b)\n" +
                "\n" +
                "# rdd转为collecton并打印\n" +
                "resultColl = resultRdd.collect()\n" +
                "for line in resultColl:\n" +
                "\tprint line\n" +
                "\n" +
                "# 结束\n" +
                "sc.stop()\n";

        String str2 = "PK\u0003\u0004\n" +
                "  \b  唵擮            \u0004 \u0004 com/\uE491  PK\u0003\u0004\n" +
                "  \b  唵擮            \b   com/brd/PK\u0003\u0004\n" +
                "  \b  唵擮            \u0010   com/brd/orctest/PK\u0003\u0004\u0014 \b\b\b i\\扥            ,   com/brd/orctest/ReadOrcFile$$anonfun$1.class漋KS踁\u0014\uE475\u001F抦L 兺3\uE4CF€\u001A\u001C\"LS7岻殧Gk?郂\u0013易??!)挏譒\uF8F5Aw漣哃鱨?H\u001F3櫘篽W]t覷洘验際v\t墎0?漿?忥<顏\u001F\uF8F5\uE4BD\t€q(\fC脖!暚瞕X玻貛搐痱?熄?0纔C_\uE0A7\u0003i\u0011屷n迻蛊%\uE067;陠\"]*賻舉gΚ藥j栝壖aU$nry]憀揫7%麞&-\u0019w铲凕\\-[S娒U-{![鱍T,昸陖^覕,C\uF8F5K<?0?UE\b\fm粅濚d\u0018??\"?剜\uE02D怘^线喾工q?\u0015\u001DK?籴??eh?g+奺\u0013屽?\u0003?\uE771a\br釉?L&鱋宛!s眯?若]Y1菪Et0t韌\\D?\\Q?p唜27?O\u0014]鑞B'z\u0018|g3!鬛h/J?F鹅獩{蟅\"欅圬+8\u001EA?N0\b虱*譴罪.羺?Ev裁玅\f`?\u001Dr暫軺??a劻\u007F&3\u0016??犛\f儑J搱1r;￡猻佱媎#綷畱w\u0018?}\uE863骒祆?4幾\"H?Cs瓀/[JYY??垍叐妩?CK\uE480S署Y??8?♀\u0016\u0014坻\u0015和\u001D??侒\u0011腁?Mb9毦OA\b褽\\姁宸?8?U.y`{>\u001B\u0011詿~烁眯?\uE090??潗?q2\u001C1-E6舨?鯴?窥\"s?d\u0019|霉g?枺\t禬 崿(bh徖囁遭酚?ㄋP\u0004蒢Y瓺\u0010?C?眿x忣肗鉗\n" +
                "'\n" +
                "輛割琾璊?揊?諣戥p\u001A\u00144懏?筏\uE00D蘔7J奤?\u0017\u001D.?p尘\u000F\u0017諍螡狤際穴Z测z%読]?汻VP渦Ｌ\u0010烷篵Mj芏\u0015猉K秧枩畱嫓緁P?\u000Eu殢垄.牕\u001BG蠦t曟?\uE169Ｍ?q\uE1AA鄗B矦\uE638??酊0_??vt?芷蒻己夝(-囩c\uE7F2?鳂壺hJ黲?b`\u000B\u0001\u007F&\u0010揯.\u0014?\u001E\"\u0014\u000Fnb諢\u0011v媗\"?d?\b\uE511?易R\u0001颒?u\uE4DA?田\u000F鳪S邻&\u001C\u000E\uE720k?沲-闯-?綛鲈6挹?{D\u0011\u0003啃7N9x熤\u0017 笐c\n" +
                "愚\u0014\u007F?糃R!鼕w??<??0a!O?>?\n" +
                "??Hg啿\t?B\u0002C斔9\u001A?妩>zH?煝\u000F熱(>?|MS????ぇ\u0003繎诛D?酤N7鼸[\u0011\uE430/?q捁?z簶?滬?鵇髯\t&\">'\"莺H礬ㄕ\u0012绌?⑨懺麒芷?\uE219n▔诹N╝,\uE4CEjn\b倵\u0018??訫ID]np?V拒1\u0013?雈v+\u000B\uE25B擈鉩弳缐\u0006\u0010A3J磰\u0011/崼窮畗\u001E?犬&\uE424醱\u001D37\u0010髡蜳v?\u0005PK\u0007\b?S7?  ?  PK\u0003\u0004\u0014 \b\b\b i\\扥            ,   com/brd/orctest/ReadOrcFile$$anonfun$2.class峊[O\u0013A\u0014\uE46D\u0017*e避晪鄥K/蔤糩B\u0010*IM慇?蕈邸,lg觎枲\u007F?\u001F???M黁瞥?蠪d\u001F鏈9{蝫n_媲舷_ 獭?m﹩Y?Si?甮n\b^[又婍埳I.曑i缮?\u0018C踌Z茚\uE6D4I蟦\b驣挣4芳晼?[牲|?礂5]\u0014\u001E窛By?p俞瞡甎鲃?\u0016\n" +
                "!vEh??^u\u0004?駸??\uE329C\u0013鑐?晑6餫\u0018;\uE07F\u0004:\u0019:\u001BB讌\u007Fg豋棔疀忌璢a篗\uE1CEM]珯\u001B舃↓忹?+綱\u0011甂諠畛鈏[???鑲列?ml\t鞢?\u0015\u0019爻8聫!螞M?C6}蝍g?ξ鍤?C蘮?聾:觀絹!\\Jb\u0010椛羡礭喅?!\u0010?尕抐:s魏墰関\uE54A贛\u0006\uE31F烡\u0004\u0013\f#\u007F騟I\u001D\n" +
                "w?7閽?\u0006＊\u000E7諕R庎抋(絵\u001A0??L&q\u001DS\f憙<1?+C羌-mo?毼l\u0019?汥\u001C9喲3\u001AL?Q旄K嗩e%]廗o?-\u001AOlY談烸Q'[湼@t摁\u000F員?\uE24F炸\n" +
                "\u001DZ?\u001E扶Wy3紈V旌鋇K摓\uE0C3柖B&\uE763J薗.mqUx华F?I)舨胅W?S\u0015\u007Fr%??w\u00141-?O搷\u0013耔\t?.\"E?Q1?D央\u000F?y铂搶愳蛚C2w勧颒D?\u0016{G?? ?\u000E\u0018羑犁^屷\n" +
                "呡!絻$\b鑞燝虎嫭g澿荂鳨婖\uE5D2l?\u0006弍?\uE793\u0007厹 \u001B侨\u0006f慆\u000B欩K\n" +
                "袻捑5烗垱\u001F巃屵?鎡p\u0007?^Q??< \u0019C\u0012輝HZ\u001F?1K罎A)~s忚歆??f\uE46F點溼?\uF8F5\u0017PK\u0007\b?鑋?  ?  PK\u0003\u0004\u0014 \b\b\b i\\扥            ,   com/brd/orctest/ReadOrcFile$$anonfun$3.class峊]S覢\u0014=?\uE126硫??\u0005ZPb?\uE7A6孋e\uE6B4晳h亲m矓@憨?￣*}?\u001F\u001C燒Q?%僠睊嚱浕鳛{钔绳\uE400?€Gx贪?]\uE06A\\+P?《v\u0005ww敵睐\uE53A?恵?\uE160?z托?稵$底\u0015殖v?w魐$\u001D?埠?嗹蜺u」缱?鼒[>?酥蕮澸f-炼咈革}鈓_?\u0016\uE8181?螟`j\u000EY啽3兆?\fs?鎼g葁呹堷濁败\fT氢=铎\u000B+靟uh)椎v膈卩I\uE271缝xg?$v\u0017鬥?瀴?\u0019F脋\u001B-\uE4E8埛?\u0003{a y\u0018\f揠?\u0019j錕\u000E?葌a馬?L2d糿?L? 21嶋\u0005L?砰}/d\u0018N霃\uE56DD鶦┦皘?H\uE0A23\fv?卵绾K\\&?Q@\n" +
                "\u000B\f??>\u0018脨蒺ぇ7塃挂2q\u000F?\f,2?\uE518?\t鋽\u0013萌V C庭nq?\"羋?W?\u0003抂嬘?1緣\u0017哹訐釻詍\u000B晉蚨讘\\G婗\u0005;垟撹\uE1CF\\:~\u0010覉_\n" +
                "??fCJ《|\u001E唫\uE6C2碿97\uE5F5????T﹐僤B?,fp\n" +
                "E?$?F懫X<\u001A颥掔\t?Yc?3燐!鱥?q?f麏30嘯\u0014鷢鯵睝u?撂'0O)>稳\uE1E1\uE487?n\uF8F5\n" +
                "?吺?顬嚔?烜Ydc\uE1E6?K_Na?g\u0002s69嫷~T歊c{\u0005\u000F蒮P?潅X\uE6AE沞T╰狾a宯=`?硉\\樑髨梱嗸\uE28F7PK\u0007\b?n攣\u0002  \u001B\u0005  PK\u0003\u0004\u0014 \b\b\b i\\扥            7   com/brd/orctest/ReadOrcFile$$anonfun$4$$anonfun$5.class峊[O\u0013A\u0014\uE46D\u0017謻6\\E\u0005\u0015/\u0005zQj龚XB\u0010悿\uE6AEへ\u0018蕈?\u0016棛fw枲\u007F?\u001F???\u0013\u007F旕旌\u0001うv\u001F螜=3?鐋缣\uE407貂\u001B€\u0019?J?,6渇Q9??n\t捃t蘵??桱顉23w稖7?ZU卒6/:炘帧(>m岗幡^鳏?%K嬚N蜡浳毿懿苏\u0003~膵6梴磐苼0uy?b讋cq踷?稜L?\u0013袬宎拜誁\u000F美硅恿\u0019喩頙7恅鑧\u0003暮p\\婖\uE011评炃\u0011~\fq辥賝\u0019蛸.胛?Ltu岳 ?d?沘8?荵M;栜+?勧$\u0006q?\uE798-梐禼\"\uF8F5??=翁0LuY\u0004q榤o`\uE1B4擝\u001F鷵堗:描遾\\Q锹蒡??干恓ㄣm耽?竏\u0018深?\f韯x\u000B窊\u0018?喨Q??暋g褣朸b(t钏\u0019\u001D錦漍孺?L\"汥\u001C9偵(O\u000B嚒k\u0014啽\u000E'\n" +
                "躦H滘葠^U艺\\??jrlU5卂?\uE1C1淔?y?\f}UK?轪C8?]榆|沉[?\uE54C鞩?囍蓺?S鳴甚?m逡膌\b蒋欴vEJ岈谲u\u0005\n" +
                "M_惋ME??w\u0015\n" +
                "v`A墇\u0017?\uE7A9弅鐴婔\u0003汓\b覉b纎/YJd\uE7F0帎N\u0017N0?F?b?d堃?岦倌\u0015\\\n" +
                "甆氞F蒭栔=\uE626菢#\u0019G\uE61D欟?浔\u0010y橽}鏣攫\u001D?格\u0005w?\u00069?\uE714丼萡?0?\uE6A8磪0蟲?驘.滥Oa?噢\u0005??纾x\u0018鐺xD:哾??JWq\u001AE$\u0002\u001B垹H?H\u000E?\u001A鮏a>o?cx怙\uF8F5\u0006PK\u0007\b\u000B補\uE3C2\u0002  ?  PK\u0003\u0004\u0014 \b\b\b i\\扥            7   com/brd/orctest/ReadOrcFile$$anonfun$4$$anonfun$6.class峌ks跠\u0014=胴Vl陨?4ゴ\n" +
                "nk'P懚?囆&N@俩?1\n" +
                "瘷导q晳V\u001Ei??> \u001F?漚\uE25F廱??疘?懋铑瀧罟魇\uF8F5\uE406鏮 钼'?'瓠v乇傂Q\"R枝酀F璎篂(暩\f鋘,K鬟,?0哵=r盖?柺鯀酲\u001D\uE7EE;j-枎r\u00039稾?\u000F鳼'?臸痁咩/稿q俚\u001A?屺闞5舗娦鍨?o{⑹p?\u001E\n" +
                "d\u0019芆_5恎\u0018;恺Nr嗎謄?(0孎\tbK?\uE2CB秌\fl=圁菒憬烑3肔y栏+6C㎡?<O$1X\uE1C6荍摱?te费??\\锜|\u0012妿?\u0019榒06\u001A淀鷍塧\uE624l穓?&\u000B笀w\u0019?y曍WV吁{灗q%謧戌世e唽??C~褧甖b窽>拺>塲\uE66B?>(b\n" +
                "?n\u000E\u0014爜\u001B腝葞N\u0016门r?畨\u000FQ*b\u001A7)B\u001Ew}!U\u007F嫆s\u0016彸 n\uE5A2\uE5FE+E宬vy\u0015?\n" +
                "0T\uE183涾\b\u001F雿;つ耱Y\u000E鯡??z1?C?靜\uE67D'孰G}i#y簨{E提>9費\u0014<?\uE1583Y毋3-??\u0015豏%黮\u0013煟\uE058-2d誷7b竪n郎賽?Y蠒を?嬶砟S搲\u0011?懥\n" +
                "儥\bD蹺W?揺\uE395呍N7V宝e鷬婋?u鋓T嗁筵銶楾{塗I\u0005謰萢冧/\u0005币\u001C\u0006Fa竢蜪\u0003O)\uF8F5囼P1?2R\\?鱞??t?%?jq?P媙锳w\u001FI\uF8F58鲔\"L-叇蹠\\?瓔?\u000E\u001D〗醒U閤AD\u0015?泽燙氌R妏陪Q$(?M]６再村n@諕XH?匩\n" +
                "E蟚?撴-\uE33Fdp\u0001C\u0018覻\"?Y?LV`?苭\uE487{??\uE4C5K\\O遟\u000Fp?3/A\u000B?>=缆\uF8F5痏\f{??\u001A'?X聴?n\u001E\u000F駡||K?屠妈S蚧滫}H珲\n" +
                "sf鰋L\u001F狏\n" +
                "_\uF8F5v\u0002?D3ac??\u001A\uE217?m乫}#G?\uF8F5~\u0002&w\b揅\u0003ON冷?9?劃d\u001E苭4gQゑ{z\uE6CEg\u001A沨\uE575谸騟淫\u0007\u001A'柽mHSaZU=,3\uE3B3?\u0003PK\u0007\b\uE1A4\u007F\t?  7\u0007  PK\u0003\u0004\u0014 \b\b\b i\\扥            I   com/brd/orctest/ReadOrcFile$$anonfun$4$$anonfun$apply$1$$anonfun$10.class漊踨\u0013G\u0010=瓔\u0017)r?8\\\u0012H?$\u001BXDH\b戙\u0018藃\u0010?倢+褊h56KV3\uE03CY婁O?y蒀?揓猂y?\u001FE鸦薏峂\t\u0015\uE031鬖O鏖验3?/\uF8F5?繫< 註輜篈涎乲dh渿R糈伝犍叶呉j;R霏儵\u0018\f鼰礻仯z?\u0011\u0006\uE149\u0015緋侶\u0019?澔菪\u0004?珣r崸Uu?\uE0EF鹘`E\u001A狴抵\u0013馮8綪;N畸D害禭K?2饎稞,壕?.茎\uE56A\u001Ca鷛獏1略￡讚\u0018马w$肂?\u0019&%6e\u0010r馟?\u0002菹#?騃0a?\"\u0011?楼態-L\u0011r虂G8U?b癱\u0002O碓J8塖EL?赁#恼?\u0013?\uE214廘LL\u001A???岋E>\bdOn??\uE0D0鍪\uE59C?L碸坼\u0012珩A\u0001琊!'奾?曎?\\.\u001F??Oy\u0011\u001F\u0015\u0019鈉仑偋<矵8\uE40F芡\u0012>乚?\\\"溹f4?密f藭?疇\u001CoU?筘\u000B\t崱審╮\u0016\\西?\\\u0019眮G~x\uE045蕅W\t\u0013?\"?剆瘚zY?撫肏m鹇翸B┇焟鑕瓆)\u0014a\uE788u\u00180??|V魔鴾恲Z錵p?aa???H豊\u001AQ+\"?顧?#\u0003聺w??抝a塒?橓\u001D瓗?嫴甎h?浡徃嫻侯蓸\u0018蛣o\n" +
                "净|\u00036?\u001677z=陊e恴?F?畨A?t?%L\u0014鸺匮Q嗍??旊霅e?蚦葶?晵A?a(Y?澑罬\u0015k┅???U\u0016@?\n" +
                "~Nq\u0016铩亩罯G\u0006闳b*?{V巽\"荿罏吲?葛\u000F吸?.墨]?清]听\u0002+?r龠8.媜x溬肆<?O拲k肝堶x>?XG撉<2矺4?\u0017t覀K?'楁?敏.?狯锷?€K\uE36D%|?G€塽騟?锱\u0019\uE2AA瑛陱#0鶀?\u0016聃\u0011?躉獬??衎汣懬5\uE495騶\u001Aw睂\u0002V\u0012敥d/稒援髕?辈駪(?\u001E\u001CB;?\u0005PK\u0007\b瓪\uE5B6S\u0003  \u001D\u0007  PK\u0003\u0004\u0014 \b\b\b i\\扥            I   com/brd/orctest/ReadOrcFile$$anonfun$4$$anonfun$apply$1$$anonfun$11.class漊軴\u0013A\u0010\uF8F5m课諦[@DQP,P@︰\u0014?%|$5|ㄕ谱韚)嚽\u001E观#??_癀&>\u0018燒\uE5D1崇\u0005\u0010Hm镐fvgg\u007F3\uE388櫥_靠\uF8F5 ?K\f甩砏l竿\uE76C欽x\uE057F疰杒甕度绻t涠/髎荂究o\u007F虠?\uE697\u0001瓢款欆鍱讞授\u0013钮啩\\n?_毷rd┎?喳\uE787\"\u0014缝蝥.?郋浰V宝\\K妒嬪\u0010?\\嬠?薨E檃?\u0011\n" +
                "?\uE31Dn5恅葷?\u001B?蘝?\u0003I啲\u0017劏\u000B祝囡?\f靍\u001C崦\u0010\u000F?蕝.墭:?脁W{\n" +
                "?bD娕0P8\u0007(崀\fば?旞閁\u0003W\u0019\u0012稅-得\u0010-LU痈嗠)\fa?轾歨騝\u0018,T\uE0A4⑦膱v\u001F?詭?瑅<t梾F鐝贃\u0014\f揮r?Q8戇VcW橁\u001FfCS\u001A\u0019dS坄?rP⒑9隒\uE5FA,i〦咼鏚靝烌T=啻炂4瞀\u0010?峦;?.寐卆\u0019?l5@GJ\u001E馚磘\u0017抢\u001CC喜#=钮猻?騝薔Sh?j?ИЕz?Cf濐g舆k\b7呆?7?l瘕p灛Y-蓵镆8Us|?:(m]暒韝T@\u001BB?M唗UJ?圮?誔Ζ鄞*u\tT宥C=\u0015XP\uE561婼嬔噲\n" +
                "?窑\u001FS覧袃(r鷍?O??\uE5EDf?l鉌6?)=\uE846\u001E\uE11B俎6n\u001D扜\u0014OH\u000E\uE4BC苖?m<???峽?丌NzE蹙収43\u0010榀\u001A\u0018示\uE705鉧術\u0004\uE547捰3?炎其a愛1~???\uE6A8?i`撲4fB?襷G|?f繛倝\u001F聊Q凝S0\t?\uF8F5(*伨Dy\u00011\uE5EE.?F??<?\u001E\u0005(筦M際\uE7C5撿\uE754}T\uE7A24痁?夹? PK\u0007\bR?.?  X\u0006  PK\u0003\u0004\u0014 \b\b\b i\\扥            I   com/brd/orctest/ReadOrcFile$$anonfun$4$$anonfun$apply$1$$anonfun$13.class漊]o\u001BE\u0014=?仝8蔊坌\u000Fh!荻v\u0002]?Jq\bi\u001CЦ8u┯ㄍ\u000B\u001A?闁醅?\u001B\u0015?縺\u0017\u001E€?亜x鈦\u001FU跷f暏Ie琙蜍?鳛;>魈\uE252???\u001E\u0010阯衱篴?BW薍;\u000Fヨ礐w阵\uE673\u000B\u0015ōX?\u000F\uE6C80?矮\u0007庩\n" +
                "\u000BD\u0018?W\uE255\tcソ総顃#\u001D";

        System.out.println(isMessyCode(str));
        System.out.println(isMessyCodeUnicode(str));
        System.out.println(hasMessyCode(str));

        System.out.println(isMessyCode(str2));
        System.out.println(isMessyCodeUnicode(str2));
        System.out.println(hasMessyCode(str2));
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /*
     * 判断是否为乱码
     *
     * @param str
     * @return
     */
    public static boolean isMessyCodeUnicode(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // 当从Unicode编码向某个字符集转换时，如果在该字符集中没有对应的编码，则得到0x3f（即问号字符?）
            //从其他字符集向Unicode编码转换时，如果这个二进制数在该字符集中没有标识任何的字符，则得到的结果是0xfffd
            //System.out.println("--- " + (int) c);
            if ((int) c == 0xfffd) {
                // 存在乱码
                //System.out.println("存在乱码 " + (int) c);
                return true;
            }
        }
        return false;
    }


    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = 0 ;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
                chLength++;
            }
        }
        float result = count / chLength ;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean hasMessyCode(String str){
        return !java.nio.charset.Charset.forName("GBK").newEncoder().canEncode(str);
    }


    public static String toChinese(String msg){
        if(isMessyCode(msg)){
            try {
                return new String(msg.getBytes("ISO8859-1"), "UTF-8");
            } catch (Exception e) {
            }
        }
        return msg ;
    }

    /*
     * 判断是否为汉字
     *
     * @param str
     * @return
     */
    public static boolean isGBK(String str) {
        char[] chars = str.toCharArray();
        boolean isGBK = false;
        for (int i = 0; i < chars.length; i++) {
            byte[] bytes = ("" + chars[i]).getBytes();
            if (bytes.length == 2) {
                int[] ints = new int[2];
                ints[0] = bytes[0] & 0xff;
                ints[1] = bytes[1] & 0xff;
                if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40
                        && ints[1] <= 0xFE) {
                    isGBK = true;
                    break;
                }
            }
        }
        return isGBK;
    }

    /*
     * 判断字符串是否为双整型数字
     *
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        if ($.isEmptyOrNull(str)) {
            return false;
        }
        Pattern p = Pattern.compile("-*\\d*.\\d*");
        // Pattern p = Pattern.compile("-*"+"\\d*"+"."+"\\d*");
        return p.matcher(str).matches();
    }

    /*
     * 判断字符串是否为整字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if ($.isEmptyOrNull(str)) {
            return false;
        }
        Pattern p = Pattern.compile("-*\\d*");
        return p.matcher(str).matches();
    }

    /*
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ) {
            return false;
        }
        return true;
    }
}
