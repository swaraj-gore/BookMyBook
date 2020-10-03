package com.swaraj.bookmybook;



public class Book {

    private String Title;
    private String Description ;
    private String mImageUrl;
    private boolean isRequested= false;

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    private boolean isAccepted=false;

    public boolean isRequested() {
        return isRequested;
    }

    public void setRequested(boolean requested) {
        isRequested = requested;
    }


    public String getRequester_id() {
        return requester_id;
    }

    public void setRequester_id(String requester_id) {
        this.requester_id = requester_id;
    }

    String requester_id;

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    private String book_id;

    public Book() {
    }

    public Book(String title, String description, String imageUrl,String book_id) {
        this.Title = title;
        this.Description = description;
        this.mImageUrl=imageUrl;
        this.book_id=book_id;
    }


    public String getTitle() {
        return Title;
    }


    public String getDescription() {
        return Description;
    }



    public void setTitle(String title) {
        Title = title;
    }



    public void setDescription(String description) {
        Description = description;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

}
