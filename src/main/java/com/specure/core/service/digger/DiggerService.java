package com.specure.core.service.digger;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class DiggerService {

    private final DiggerCommandPerformerService digger;

    public Long digASN(InetAddress address) {
        String ip6Postfix = "origin6.asn.cymru.com";
        String ip4Postfix = "origin.asn.cymru.com";
        String postfix = (address instanceof Inet6Address) ? ip6Postfix : ip4Postfix;
        String digCommandGetASN = "dig -t txt %s.%s +short";
        String command = String.format(digCommandGetASN, reverseIp(address), postfix);
        return getAsnFromDigResult(digger.dig(command));
    }

    public String getProviderByASN(Long asn) {
        String digCommandGetProviderName = "dig -t txt AS%s.asn.cymru.com. +short";
        String command = String.format(digCommandGetProviderName, asn.toString());
        return getProviderNameFromDigResult(digger.dig(command));
    }

    public String reverseIp(InetAddress address) {
        final byte[] addr = address.getAddress();
        if (addr.length == 4) {
            List<String> listOfParts = Arrays.asList(address.toString().substring(1).split("\\."));
            Collections.reverse(listOfParts);
            return String.join(".", listOfParts);
        }
        final StringBuilder sb = new StringBuilder();
        final int[] nibbles = new int[2];
        for (int i = addr.length - 1; i >= 0; i--) {
            nibbles[0] = (addr[i] & 0xFF) >> 4;
            nibbles[1] = addr[i] & 0xFF & 0xF;
            for (int j = nibbles.length - 1; j >= 0; j--) {
                sb.append(Integer.toHexString(nibbles[j]));
                if (i > 0 || j > 0)
                    sb.append(".");
            }
        }
        return sb.toString();
    }

    public Long getAsnFromDigResult(String result) {
        if (result.isEmpty()) {
            return 0L;
        }
        String asn = result.substring(1).split(" ")[0];
        return Long.valueOf(asn);
    }

    public String getProviderNameFromDigResult(String result) {
        String[] rawList = result.split("\\|");
        int lastIndex = rawList.length - 1;
        String rawProviderName = rawList[lastIndex].trim();
        return rawProviderName.substring(0, rawProviderName.length() - 1);
    }
}
