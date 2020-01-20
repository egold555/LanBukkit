package org.golde.bukkit.lan;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//Modified from :net.minecraft.client.multiplayer.ThreadLanServerPing
public class ThreadLanServerPing extends Thread
{
    private static final AtomicInteger field_148658_a = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private final String motd;

    /** The socket we're using to send packets on. */
    private final DatagramSocket socket;
    private boolean isStopping = true;
    private final String address;

    public ThreadLanServerPing(String p_i1321_1_, String p_i1321_2_) throws IOException
    {
        super("LanServerPinger #" + field_148658_a.incrementAndGet());
        this.motd = p_i1321_1_;
        this.address = p_i1321_2_;
        this.setDaemon(true);
        this.socket = new DatagramSocket();
    }

    public void run()
    {
        String s = getPingResponse(this.motd, this.address);
        byte[] abyte = s.getBytes();

        while (!this.isInterrupted() && this.isStopping)
        {
            try
            {
                InetAddress inetaddress = InetAddress.getByName("224.0.2.60");
                DatagramPacket datagrampacket = new DatagramPacket(abyte, abyte.length, inetaddress, 4445);
                this.socket.send(datagrampacket);
            }
            catch (IOException ioexception)
            {
                logger.warn("LanServerPinger: " + ioexception.getMessage());
                break;
            }

            try
            {
                sleep(1500L);
            }
            catch (InterruptedException var5)
            {
                ;
            }
        }
    }

    public void interrupt() {
        super.interrupt();
        this.isStopping = false;
    }

    private String getPingResponse(String p_77525_0_, String p_77525_1_) {
        return "[MOTD]" + p_77525_0_ + "[/MOTD][AD]" + p_77525_1_ + "[/AD]";
    } 

}

