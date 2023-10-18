import java.io.*;

public class QueryLinkedList {
    QueryNode head;
    String outputFilePath="C:/Users/LENOVO/Desktop/output.txt";

    public void load(String txtFilePath) throws IOException {
        QueryLinkedList queryLinkedList = new QueryLinkedList();

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
                        if (!queryWordExists(word)) {
                            addNewQueryNode(word, docName);
                        } else {
                            QueryNode currentNode = get(word);
                            if (!currentNode.docsLinkedList.docsNameExist(docName)) {
                                currentNode.docsLinkedList.addNewDocNode(docName);
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
        QueryNode walk=head;
        while(walk!=null){
            if(walk.wordValue.equals(query)){
                try {
                    FileWriter fileWriter=new FileWriter(outputFilePath);
                    fileWriter.write("query "+query+"/n"+walk.docsLinkedList.docsLinkedListToString());
                    System.out.println("query"+query+"/n"+walk.docsLinkedList.docsLinkedListToString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            System.out.println("oi");
            walk=walk.nextWord;
        }
    }

    public void remove(String documentName){
        if(head==null) return;
        QueryNode walk=head;
        while(walk!=null){
            walk.docsLinkedList.removeDocNode(documentName);
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
        if(head==null){
            head= newQueryNode;
        }else{
            QueryNode walk=head;
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
