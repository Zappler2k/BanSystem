package de.zappler2k.bansystem.ban.file.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Mute {

    private String id;
    private String reason;
    private Integer Days;
    private Integer Hours;
    private Integer Minutes;
    private Integer Seconds;
    private boolean permanent;
    private boolean ipBan;
}
