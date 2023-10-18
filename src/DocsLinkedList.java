public class DocsLinkedList {
    DocsNode head;
    int length;

    public void addNewDocNode(String docName){
        DocsNode newDocNode=new DocsNode(docName);
        if(head==null) head= newDocNode;
        DocsNode walk=head;
        while(walk.nextDoc!=null){
            walk=walk.nextDoc;
        }
        walk.nextDoc=newDocNode;
        length++;
    }
    public void removeDocNode(String docName){
        if(head==null) return;
        DocsNode walk=head;
        DocsNode prev=head;
        int index=0;
        while(walk!=null){
            if(walk.docName.equals(docName)&&index==0){
                walk=walk.nextDoc;
                head.nextDoc=null;
                head=walk;
                return;
            }else if(walk.docName.equals(docName)&&index==length-1){
                prev.nextDoc=null;
            }else if(walk.docName.equals(docName)){
                prev.nextDoc=walk.nextDoc;
                return;
            }
            prev=walk;
            walk=walk.nextDoc;
            index++;
        }
    }
    public String docsLinkedListToString(){
        if(head==null) return null;
        String docsString="";
        DocsNode walk=head;
        while(walk!=null){
            if(walk.nextDoc==null){
                docsString+=walk.docName;
            }else{
                docsString+=walk.docName+',';
            }
            walk=walk.nextDoc;
        }
        return docsString;
    }
    public boolean docsNameExist(String docName){
        if (head==null) return false;
        DocsNode walk=head;
        while(walk.nextDoc!=null){
            if(walk.docName==docName){
                return true;
            }
            walk=walk.nextDoc;

        }
        return false;
    }

}
