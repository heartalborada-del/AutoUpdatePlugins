package io.github.aplini.autoupdateplugins.beans.CurseForge;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class CurseForgeData {
    private String fileName;
    @SerializedName("downloadUrl")
    private String fileUrl;
    private String gameVersion;
}
