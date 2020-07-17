package tnt_deploy;

import java.io.*;
import java.nio.file.Files;

public class Test {
    public static void main(String[] args) throws IOException {
        String filepath  = System.getProperty("user.dir") + "\\src\\tnt_deploy\\goods_info.data";
        GoodsInfo.goods_info_ARRAY array = GoodsInfo.goods_info_ARRAY.parseFrom(new FileInputStream(filepath));
        //        AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.parseFrom(new FileInputStream(filepath));
        System.out.println(array.getItemsCount());
        System.out.println(array.getItems(1).getDescription().toStringUtf8());
        BufferedWriter writer = Files.newBufferedWriter(new File("goodsinfo").toPath());
        writer.write(array.toString());
    }
}
