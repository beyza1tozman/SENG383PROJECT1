import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        String filePath= "C:/Users/LENOVO/Desktop/information.txt";
        QueryLinkedList queryLinkedList=new QueryLinkedList();
        queryLinkedList.load(filePath);
        queryLinkedList.search("science");
    }
}