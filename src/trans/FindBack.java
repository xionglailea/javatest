package trans;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by xiong on 2016/10/12.
 */
public class FindBack {

    public static Map<Integer, Map<Long, briefInfo>> costInfo = new HashMap<>();
    public static Map<Integer, Set<Long>> wrongRoles = new HashMap<>();

    public static void main(String[] args) throws IOException {
//        String s = "{\"logtime\":\"2016-10-12 00:01:46\",\"logname\":\"costyuanbao\",\"serverid\":\"49\",\"os\":\"2\",\"platform\":\"vivo\",\"userid\":\"1898334\",\"account\":\"38_4aab821f745d39ef\",\"roleid\":\"200421425\",\"rolename\":\"蓦然灬少卿\",\"lev\":\"45\",\"totalcash\":\"2800\",\"yuanbaotype\":\"10200001\",\"yuanbaopath\":\"36\",\"yuanbao\":\"300000\"}";
        File home = new File(System.getProperty("user.dir") + "\\data");
        for(File file : home.listFiles()){
            String fileName = file.getName();
            int serverid = Integer.parseInt(fileName.split("_")[3]);
            Map<Long, briefInfo> serverInfo = new HashMap<>();
            System.out.println(file.getAbsolutePath());
            List<String> infos = Files.readAllLines(Paths.get(file.getAbsolutePath()));
            for(String costinfo : infos){
                costYuanbao result = null;
                try {
                     result = JSON.parseObject(costinfo, costYuanbao.class);
                }catch (Exception e){
                    String changeName = replaceName(costinfo);
                    result = JSON.parseObject(changeName, costYuanbao.class);
                }
                briefInfo roleinfo = serverInfo.get(result.getRoleid());
                if(roleinfo == null){
                    roleinfo = new briefInfo();
                    roleinfo.setRoleid(result.getRoleid());
                    serverInfo.put(result.getRoleid(), roleinfo);
                }
                if(result.getYuanbaotype() == 10200001){
                    roleinfo.setCostjinbi(result.getYuanbao() + roleinfo.costjinbi);
                }else if(result.getYuanbaotype() == 10200002){
                    roleinfo.setCostyuanbao(result.getYuanbao() + roleinfo.costyuanbao);
                }
            }
            costInfo.put(serverid, serverInfo);
            System.out.println("serverid: " + serverid + "; 数量: " + serverInfo.size());
        }
        for(Map.Entry<Integer, Map<Long, briefInfo>> e : costInfo.entrySet()){
            Set<Long> rolesByserverid = new HashSet<>();
            wrongRoles.put(e.getKey(), rolesByserverid);
            for(Map.Entry<Long, briefInfo> roles : e.getValue().entrySet()){
                briefInfo detail = roles.getValue();
                if((detail.costjinbi / 40000 + detail.costyuanbao) > 600){
                    rolesByserverid.add(detail.roleid);
                }
            }
        }
        System.out.println(wrongRoles);
    }

    private static String replaceName(String line) {
        int index1 = line.indexOf("rolename\":") + 11;
        int index2 = line.indexOf(",\"lev") - 1;
        String name = line.substring(index1, index2);
        line = line.replace("rolename\":\"" + name + "\"", "rolename\":\"default\"");
        return line;
    }

    public static class briefInfo{
        long roleid;
        int costjinbi;
        int costyuanbao;
//        public briefInfo(long roleid, int jinbi, int yuanbao){
//            this.roleid = roleid;
//            this.costjinbi = jinbi;
//            this.costyuanbao = yuanbao;
//        }


        public void setRoleid(long roleid) {
            this.roleid = roleid;
        }

        public void setCostjinbi(int costjinbi) {
            this.costjinbi = costjinbi;
        }

        public void setCostyuanbao(int costyuanbao) {
            this.costyuanbao = costyuanbao;
        }

        @Override
        public String toString() {
            return roleid + " " + costjinbi + " " + costyuanbao;
        }
    }

}
