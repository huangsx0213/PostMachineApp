package PostMachineApp;

import java.util.List;

public class Post {

    public static void Post(List<ForumPost> ForumPosts) {
        for (ForumPost ForumPost : ForumPosts) {
            PostThread PostThread = new PostThread(ForumPost);
            new Thread(PostThread).start();
        }

    }
}
