package com.monk.moduleannotation.reflect;

import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IInterface {

    void postLogin(@Query("api/test")String path);
}
