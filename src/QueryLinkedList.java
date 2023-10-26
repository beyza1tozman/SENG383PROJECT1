import java.io.*;
import java.util.Arrays;
import java.util.HashSet;

public class QueryLinkedList {
    QueryNode head;
    String outputFilePath="C:/Users/LENOVO/Desktop/output.txt";

    public void load(String txtFilePath,QueryLinkedList queryLinkedList) throws IOException {


        String docName = null;
        boolean isFirstLine = true;
        int lineWithHashTagCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(txtFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.equals("###")) {
                    lineWithHashTagCount++;
                    continue;
                }
                if (lineWithHashTagCount == 2) {
                    docName = line;
                    docName=docName.toLowerCase();
                    lineWithHashTagCount = 0;
                    continue;
                }
                if (isFirstLine) {
                    docName = line;
                    docName=docName.toLowerCase();
                    isFirstLine = false;
                } else {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                        if(!word.isEmpty()) {//wordum boş deilse ve linkedlistte aynı kelimelere izin vermeme şeyini yaptın sen burada. sciencetan iki tane olmicak mesela query linked listimizde ve bunu kontrol ederek ekliyorsun ama bi de alfabetik olarak ekleme özelliğini de yapabilirsin burada
                            if (!queryWordExists(word)) {//science kelimesi yoksa eklicek. olup olmadığını iyi kontrol edemio sanırım.. yok ediomuş çünkü else kısmına geldi. demek ki science nodu var die
                                queryLinkedList.addNewQueryNode(word, docName);
                            } else {//science kelimesi varsa . o nodu alıp onun documan linked listinde var mı die bakıcak computer science. varsa eklemicek yoksa eklicek
                                QueryNode currentNode = get(word);//sıkıntı burada ya da önümüzdeki 2 satırda
                                if (!currentNode.docsLinkedList.docsNameExist(docName)) {
                                    currentNode.docsLinkedList.addNewDocNode(docName);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search(String query) {
        if (head == null) {
            return;
        }

        HashSet<String> result = new HashSet<>();
        HashSet<String> notWantedDocs = new HashSet<>();
        String[] words = query.split(",");
        boolean wordExists=false;
        int existenWordCount=0;
        int notExistentWordCount=0;

        for (String word : words) {
            boolean isNotWanted = word.startsWith("!");
            if (isNotWanted) {
                word = word.substring(1);
            }

            QueryNode walk = head;
            while (walk != null) {
                if (walk.wordValue.equals(word)) {
                    String[] docs = walk.docsLinkedList.docsLinkedListToString().split(",");
                    if (isNotWanted) {
                        notWantedDocs.addAll(Arrays.asList(docs));
                    } else {
                        if (result.isEmpty()) {
                            result.addAll(Arrays.asList(docs));
                        } else {
                            result.retainAll(Arrays.asList(docs));
                        }
                    }
                    wordExists=true;
                    break;
                }
                walk = walk.nextWord;
            }
            if(wordExists){
                existenWordCount++;
                wordExists=false;
            }else{
                notExistentWordCount++;
            }

        }
        if(notExistentWordCount>0){
            result.clear();
        }
        result.removeAll(notWantedDocs);
        System.out.println("query " + query + "\n" + result + "\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, true))) {
            writer.write("query " + query + "\n" + result);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void remove(String documentName){
        if(head==null) return;
        documentName=documentName.toLowerCase();
        QueryNode walk=head;
        while(walk!=null){
            walk.docsLinkedList.removeDocNode(documentName);//her kelimenin documentlinkedlistinden çıkarmak istemişsin.öyle bi documentname varsa çıkarıcak yoksa da bişi yapmıcak artık
            walk=walk.nextWord;
        }

    }
    public void clearList(){
        makeLinkedListEmpty();

        try {
            FileWriter writer = new FileWriter(outputFilePath);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void addNewQueryNode(String word, String docName) {
        QueryNode newQueryNode = new QueryNode(word);
        DocsLinkedList newDocsLinkedList = new DocsLinkedList();
        newDocsLinkedList.addNewDocNode(docName);
        newQueryNode.docsLinkedList = newDocsLinkedList;

        if (this.head == null) {
            this.head = newQueryNode;
        } else if (word.compareTo(head.wordValue) < 0) {
            newQueryNode.nextWord = head;
            this.head = newQueryNode;
        } else {
            QueryNode current = head;
            QueryNode prev = null;
            while (current != null && word.compareTo(current.wordValue) > 0) {
                prev = current;
                current = current.nextWord;
            }
            if (prev != null) {
                prev.nextWord = newQueryNode;
            }
            newQueryNode.nextWord = current;
        }
    }

    public boolean queryWordExists(String query){
        if(head==null) return false;
        QueryNode walk=head;
        while(walk!=null){
            if(walk.wordValue.equals(query)){
                return true;
            }
            walk=walk.nextWord;
        }
        return false;
    }
    public void makeLinkedListEmpty() {
        head = null;
    }
    public QueryNode get(String word){
        if(head==null) return null;
        QueryNode walk=head;
        while(!walk.wordValue.equals(word)){
            walk=walk.nextWord;
        }
        return walk;

    }

}
