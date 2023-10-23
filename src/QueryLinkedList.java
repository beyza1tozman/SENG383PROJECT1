import java.io.*;
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
                    lineWithHashTagCount = 0;
                    continue;
                }
                if (isFirstLine) {
                    docName = line;
                    isFirstLine = false;
                } else {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                        if(!word.isEmpty()) {
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

    public void search(String query){
        if(head==null) return;
        HashSet<String>result=new HashSet<>();
        String[] words=query.split(",");
        QueryNode walk=head;
        for(String word:words){
            if(word.charAt(0)=='!'){//istemediğimiz kelime ise o kelime linkedlistimizde var mı die gezicez ve olduğu linked listleri
                word=word.substring(1);
                walk=head;
                while(walk!=null){
                    if(walk.wordValue.equals(word)){//mesela cat kelimesinin olmamasını istiyor cat kelimesini içeren bir node bulduysak. bunun documanlarını gezip hashsette varsa silicez
                        String[]whatDocumentsThisWordAppear=walk.docsLinkedList.docsLinkedListToString().split(",");
                        for(String docName:whatDocumentsThisWordAppear){
                            if(result.contains(docName)){
                                result.remove(docName);
                            }
                        }
                        break;
                    }
                    walk=walk.nextWord;
                }
            }else{//olmasını istediğimiz bi kelime ise
                walk=head;
                while(walk!=null){
                    if(walk.wordValue.equals(word)){
                        String[]whatDocumentsThisWordAppear=walk.docsLinkedList.docsLinkedListToString().split(",");
                        for(String docName:whatDocumentsThisWordAppear){
                            result.add(docName);
                        }
                        break;
                    }
                    walk=walk.nextWord;
                }
            }
        }
        System.out.println("query " + query + "\n" + result+"\n");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath,true))) {//this opens the file in append mode. and with this way we are not overwriting output files content. we are adding new lines to it.
            //output file boşsa direkt en başa yazabilirsin ama eğer içinde bir şeyler varsa en sonuna gelip sonuna eklemen lazım
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
    public void addNewQueryNode(String word,String docName){
        QueryNode newQueryNode=new QueryNode(word);
        DocsLinkedList newDocsLinkedList=new DocsLinkedList();
        newDocsLinkedList.addNewDocNode(docName);
        newQueryNode.docsLinkedList=newDocsLinkedList;
        if(this.head==null){
            this.head= newQueryNode;
        }else{
            QueryNode walk=this.head;
            while(walk.nextWord!=null){
                walk=walk.nextWord;
            }
            walk.nextWord=newQueryNode;
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
