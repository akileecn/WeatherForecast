package cn.tianya.weatherforecast.api;

/**
 * 返回结果
 * Created by Administrator on 2017/8/23.
 */
public class Result<T> {
    private Boolean success;
    private T data;
    private String message;

    public static <T> Result<T> success(T data){
        Result<T> result = new Result<>();
        result.success = true;
        result.data = data;
        return result;
    }

    public static <T> Result<T> success(){
        return success(null);
    }

    public static <T> Result<T> fail(String message){
        Result<T> result = new Result<>();
        result.success = false;
        result.message = message;
        return result;
    }

    public static <T> Result<T> fail(){
        return fail(null);
    }


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
