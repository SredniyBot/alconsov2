package com.pavi.alconsov2.service;

import com.pavi.alconsov2.entity.ResultInfo;

public interface FilesResultObserver {
    String updateAndGetResult(int scatter, boolean useN, ResultInfo resultInfo);
}
