package com.example.demo.type;

import de.brendamour.jpasskit.enums.PKDataDetectorType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BackFieldHeaderType {
    MEMBERSHIP_NUM("멤버십 번호", PKDataDetectorType.PKDataDetectorTypeAddress),
    WEB_URL("Site Url", PKDataDetectorType.PKDataDetectorTypeLink);

    private String name;
    private PKDataDetectorType detectorType;
}
