
    package Common;

    public class QuoteTweet extends Tweet{
        private Tweet originalTweet;

        public QuoteTweet(String authorUsername, String text, String photo, Tweet originalTweet) {
            super(authorUsername, text, photo);
            this.originalTweet = originalTweet;
        }
    }
