package com.github.alexthe666.iceandfire.client.particle;

import com.mojang.blaze3d.vertex.*;

/**
 * Since 1.21 the particle engine keeps the shared tesselator open while particles render, so particles that
 * draw immediately with their own texture need a separate buffer.
 */
public final class IafParticleBuffer {
    private static Tesselator tesselator;

    private IafParticleBuffer() {
    }

    public static BufferBuilder begin() {
        if (tesselator == null) {
            tesselator = new Tesselator(16384);
        }
        return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
    }

    public static void end(BufferBuilder builder) {
        MeshData mesh = builder.build();
        if (mesh != null) {
            BufferUploader.drawWithShader(mesh);
        }
    }
}
