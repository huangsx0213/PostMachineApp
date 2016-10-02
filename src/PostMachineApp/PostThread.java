package PostMachineApp;

import PostMachineApp.EntityInterface.ForumPost;

public class PostThread implements Runnable {

    private ForumPost Post;

    public PostThread(ForumPost Post) {
        this.Post = Post;
    }

    @Override
    public void run() {
        while (true) {
            if (System.currentTimeMillis() > Post.getStartTime()) {
                Post.sendPost();
                break;
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException  ex) {
                ex.printStackTrace();
            }          
        }
    }
}
