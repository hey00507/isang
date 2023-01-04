package com.isang.api.entity.response;

import java.util.Objects;

public record SessionResponse(String accessToken) {

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SessionResponse) obj;
        return Objects.equals(this.accessToken, that.accessToken);
    }

    @Override
    public String toString() {
        return "SessionResponse[" +
                "accessToken=" + accessToken + ']';
    }


}
