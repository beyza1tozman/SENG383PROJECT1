import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //2 tane kelime giremiyo ona uğraş. 2 tane kelime girince . araya ünlem işareti koyup onunla olanları çıkarma şeyini yap.

        String filePath= "C:/Users/LENOVO/Desktop/information.txt";
        QueryLinkedList queryLinkedList = new QueryLinkedList();
        queryLinkedList.load(filePath, queryLinkedList);
        queryLinkedList.search("science");//or bi kere olunca computer sciencı bi kere yazdırdı ama or 3 kere olsaydı 3 kere yazdırırdı bunu.query linkedliste mi 3 tane koyuyoruz or yoksa nerede3 tane documan adı yazılıyor merak ediyorum. her yeni kaşılaşmada documan adını eklememesi lazım
        queryLinkedList.remove("computer science");
        queryLinkedList.search("science");
        queryLinkedList.clearList();
        queryLinkedList.load(filePath,queryLinkedList);
        queryLinkedList.search("science,!metadata");

    }
}