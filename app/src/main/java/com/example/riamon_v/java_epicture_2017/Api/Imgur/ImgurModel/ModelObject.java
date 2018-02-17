package com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel;

/**
 * ModelObject is a templated class for all the send objects
 */
public class ModelObject<T> {
    protected T data;
    protected boolean success;
    protected int status;
}
