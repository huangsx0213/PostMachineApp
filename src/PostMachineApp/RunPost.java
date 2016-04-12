
package PostMachineApp;

import java.util.List;

public class RunPost {

    public static void RunPost(List<ForumPost> ForumPosts) {
        for (ForumPost ForumPost : ForumPosts) {
            PostThread PostThread = new PostThread(ForumPost);
            new Thread(PostThread).start();
        }

    }
}