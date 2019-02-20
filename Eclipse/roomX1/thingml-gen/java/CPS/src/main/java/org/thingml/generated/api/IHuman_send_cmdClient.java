package org.thingml.generated.api;

import org.thingml.generated.api.*;

public interface IHuman_send_cmdClient{
void SwitchOn_from_send_cmd(short SwitchMsg_SwitchOn_sid_var);
void SwitchOff_from_send_cmd(short SwitchMsg_SwitchOff_sid_var);
void StartListen_from_send_cmd(String MqttSetup_StartListen_ch_var, short MqttSetup_StartListen_mid_var);
}