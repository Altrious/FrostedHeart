/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package com.teammoeg.frostedheart.world.noise;

public final class Vec4 {
    public final float x, y, z, w;

    public Vec4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public final float dot(float x, float y, float z, float w) {
        return this.x * x + this.y * y + this.z * z + this.z * z;
    }
}