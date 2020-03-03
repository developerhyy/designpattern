package com.designpattern.designpattern.chainofresponsibility;


/**
 * @author joker
 */
public class ChainOfResponsibilityTest {
    public static void main(String[] args) {

        Request request=new Request.RequestBuilder().frequentOk( true ).loggedOn( true ).isPermits(true).containsSensitiveWords(true).build();


        RequestFrequentHandler requestFrequentHandler=new RequestFrequentHandler( new LoggingHandler( new PermitsHandler(new ContainsSensitiveWordsHandler(null)) ) );

        if (requestFrequentHandler.process( request )) {

            System.out.println(" 正常业务处理");
        }else{
            System.out.println(" 访问异常");
        }

    }
}
class Request{
    private boolean loggedOn;
    private boolean frequentOk;
    private boolean isPermits;
    private boolean containsSensitiveWords;
    private String  requestBody;

    private Request(boolean loggedOn, boolean frequentOk, boolean isPermits, boolean containsSensitiveWords) {
        this.loggedOn=loggedOn;
        this.frequentOk=frequentOk;
        this.isPermits=isPermits;
        this.containsSensitiveWords=containsSensitiveWords;
    }

    /**
     * 建造者模式的创建
     */
    static class RequestBuilder{
        private boolean loggedOn;
        private boolean frequentOk;
        private boolean isPermits;
        private boolean containsSensitiveWords;

        RequestBuilder loggedOn(boolean loggedOn){
            this.loggedOn=loggedOn;
            return this;
        }

        RequestBuilder frequentOk(boolean frequentOk){
            this.frequentOk=frequentOk;
            return this;
        }
        RequestBuilder isPermits(boolean isPermits){
            this.isPermits=isPermits;
            return this;
        }
        RequestBuilder containsSensitiveWords(boolean containsSensitiveWords){
            this.containsSensitiveWords=containsSensitiveWords;
            return this;
        }

        public Request build(){
            Request request=new Request( loggedOn, frequentOk, isPermits, containsSensitiveWords);
            return request;
        }

    }

    public boolean isLoggedOn() {
        return loggedOn;
    }

    public boolean isFrequentOk() {
        return frequentOk;
    }

    public boolean isPermits() {
        return isPermits;
    }

    public boolean isContainsSensitiveWords() {
        return containsSensitiveWords;
    }
}

/**
 * 链表方式进行创建：next指针
 */
abstract class Handler{
    Handler next;

    public Handler(Handler next) {
        this.next=next;
    }

    public Handler getNext() {
        return next;
    }

    public void setNext(Handler next) {
        this.next=next;
    }

    /**
     *  每个节点都得去处理自己的任务
     * @param request 业务数据，每个节点都要处理的一个对象，当且仅当节点返回true才会继续往下执行，返回false，直接打回，结束
     * @return
     */
    abstract boolean process(Request request);

}

/**
 * 访问频率验证
 */
class RequestFrequentHandler extends Handler{
    /**
     * 当前的节点处理完
     * @param next 交给下一个节点，因为是单链表，next==null，说明是最终节点
     */
    public RequestFrequentHandler(Handler next) {
        super( next );
    }

    @Override
    boolean process(Request request) {
        //校验的代码
        System.out.println("访问频率控制.");
        if (request.isFrequentOk()){
              Handler next=getNext();
              if (null==next){
                  return true;
              }
              //下一个节点的处理
            if (!next.process( request )) {
                return false;
            }else{
                  return true;
            }
        }
        return false;
    }
}

/**
 * 登陆验证
 */
class LoggingHandler extends Handler{

    public LoggingHandler(Handler next) {
        super( next );
    }

    @Override
    boolean process(Request request) {
        System.out.println(" 登录验证");
        if (request.isLoggedOn()){
            Handler next=getNext();
            if (null==next){
                return true;
            }
            if (!next.process( request )) {
                return false;
            }else{
                return true;
            }
        }
        return false;
    }
}

/**
 * 权限验证
 */
class PermitsHandler extends Handler{

    public PermitsHandler(Handler next) {
        super( next );
    }

    @Override
    boolean process(Request request) {
        System.out.println(" 权限验证");
        if (request.isPermits()){
            Handler next=getNext();
            if (null==next){
                return true;
            }
            if (!next.process( request )) {
                return false;
            }else{
                return true;
            }
        }
        return false;
    }
}

/**
 * 敏感词验证
 */
class ContainsSensitiveWordsHandler extends Handler{

    public ContainsSensitiveWordsHandler(Handler next) {
        super( next );
    }

    @Override
    boolean process(Request request) {
        System.out.println(" 敏感词验证");
        if (request.isContainsSensitiveWords()){
            Handler next=getNext();
            if (null==next){
                return true;
            }
            if (!next.process( request )) {
                return false;
            }else{
                return true;
            }
        }
        return false;
    }
}









