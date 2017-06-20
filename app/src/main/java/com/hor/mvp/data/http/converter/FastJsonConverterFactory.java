package com.hor.mvp.data.http.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Charles on 2016/3/17.
 */
public class FastJsonConverterFactory extends Converter.Factory{

    public static FastJsonConverterFactory create() {
        return new FastJsonConverterFactory();
    }

    public FastJsonConverterFactory() {
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new FastJsonResponseBodyConverter<>(type);
    }

}
