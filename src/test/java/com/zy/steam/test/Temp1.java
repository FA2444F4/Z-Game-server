package com.zy.steam.test;

public class Temp1 {
    public static void main(String[] args) {
        String aaa=",1145360,264710,686600,285190,284160,427520,629760,560130,626690,264200,582660,417290,219150,736260,502280,743450,787480,252950,250900,632350,582160,385560,526870,609320,214560,379430,270880,590380,239140,620590,578080,400940,678950,1190460,113200,391220,570940,374320,552500,48700,1222700,531510,552520,440900,418370,447040,548430,779340,252490,636480,595520,242760,204360,1289310,1128000,240720,960090,219740,1225330,812140,648800,268910,425580,574050,206440,620,323190,269950,359550,394360,732810,105600,485510,594570,460930,258180,466560,72850,673950,428690,841370,648350,412830,421020,530070,629910,1229490,213670,8870,513710,519860,420530,1158310,292030,433340,581320,537800,390340,1015500,221380,212680,1180380,294100,594650,32470,312530,671440,637650,334040,255710,945360,631510,481510,8930,397540,271590,413410,413420,242920,240,238320,646910,207610,577800,304390,583950,753420,225540,546560,274190,677120,47890,825630,427290,322330,381210,814380,445220,739630,583470,788260,289070,39210,524580,253230,205100,236850,683320,863550,773951,2870,728880,640820,35140,447820,644930,379720,377160,364360,312660,834910,1057090,571740,7510,823130,526160,356190,339800,431960,40800,489830,588650,731490,857980,391540,582010,535930,292730,238460,281990,233860,311690,424840,275850,362890,792990,107410,698780,203160,4000,504230,646570,367520,214950,332200,712100,262060,247730,457140,723390,1113000,275390,524220,55230,476600,748490,282560,435150,221640,355790,613830,495560,1170880,250320,489940,306130,413150,644560,236510,227300,477160,782330,572410,883710,638970,346110,283640,218620\n";
        int commaCount = 0;

        for (int i = 0; i < aaa.length(); i++) {
            if (aaa.charAt(i) == ',') {
                commaCount++;
            }
        }

        System.out.println("逗号的数量是：" + commaCount);
    }
}



