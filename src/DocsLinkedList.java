public class DocsLinkedList {
    DocsNode head;
    int length;

    public void addNewDocNode(String docName){
        //docName=docName.toLowerCase(); bu satır çok saçma bi şekilde çok bozuyor
        DocsNode newDocNode=new DocsNode(docName);
        if(head==null) {
            head= newDocNode;
            length++;
            return;
        }
        DocsNode walk=head;
        while(walk.nextDoc!=null){
            walk=walk.nextDoc;
        }
        walk.nextDoc=newDocNode;
        length++;
    }
    public void removeDocNode(String docName){
        if(head==null) return;
        docName=docName.toLowerCase();
        DocsNode walk=head;
        DocsNode prev=head;
        int index=0;
        while(walk!=null){
            walk.docName=walk.docName.toLowerCase();
            if(walk.docName.equals(docName)&&index==0){
                walk=walk.nextDoc;
                head.nextDoc=null;
                head=walk;
                return;
            }else if(walk.docName.equals(docName)&&index==length-1){
                prev.nextDoc=null;
            }else if(walk.docName.equals(docName)){
                prev.nextDoc=walk.nextDoc;
                walk.nextDoc=null;
                return;
            }
            prev=walk;
            walk=walk.nextDoc;
            index++;
        }
    }
    public String docsLinkedListToString(){
        DocsNode walk=head;
        if(walk==null){
            return null;
        }
        String docsString="";
        while(walk!=null){
            if(walk.nextDoc==null){//next null ise virgülsüz ekle
                docsString+=walk.docName;
            }else{
                docsString+=walk.docName+',';//daha devamında bişi gelicekse virgüllü ekle gibi yapmaya çalışmışım
            }
            walk=walk.nextDoc;
        }
        return docsString;
    }
    public boolean docsNameExist(String docName){
        if (head==null) return false;
        DocsNode walk=head;
        while(walk!=null){
            if(walk.docName==docName){
                return true;
            }
            walk=walk.nextDoc;

        }
        return false;
    }

}
