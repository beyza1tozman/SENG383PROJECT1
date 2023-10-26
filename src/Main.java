import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {


        String filePath= "C:/Users/LENOVO/Desktop/short-poems.txt";
        QueryLinkedList queryLinkedList = new QueryLinkedList();
        queryLinkedList.load(filePath, queryLinkedList);
        queryLinkedList.search("science");//or bi kere olunca computer sciencı bi kere yazdırdı ama or 3 kere olsaydı 3 kere yazdırırdı bunu.query linkedliste mi 3 tane koyuyoruz or yoksa nerede3 tane documan adı yazılıyor merak ediyorum. her yeni kaşılaşmada documan adını eklememesi lazım
        queryLinkedList.remove("computer science");
        queryLinkedList.search("science");
        queryLinkedList.load(filePath,queryLinkedList);
        queryLinkedList.search("science,!metadata");
        queryLinkedList.search("cat,dog");
        queryLinkedList.search("cat,!dream,dog");

    }
}