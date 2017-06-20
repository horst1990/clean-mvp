package com.hor.mvp.data.http.converter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import com.hor.mvp.data.http.ApiException;
import com.hor.mvp.data.http.Response;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import static com.hor.mvp.data.http.Response.CODE_SUCCEED;

/**
 * Created by Charles on 2016/3/17.
 */
class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private Type type;

    public FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String responseStr = value.string();
        value.close();

        Response response = null;

        try {
            response = JSON.parseObject(responseStr,Response.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (response == null) {
            throw new ApiException();
        }
        if (!CODE_SUCCEED.equals(response.getCode())) {
            throw new ApiException(response.getCode(), response.getMessage());
        }
        if (response.getResponse()!=null){
            if (type == String.class){
                //noinspection unchecked
                return (T) response.getResponse();
            }else {
                JSONReader reader = new JSONReader(new StringReader(response.getResponse()));
                return reader.readObject(type);
            }
        }
        return null;
    }
}
