/*
 * This file is part of Impactor, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2018-2022 NickImpact
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package net.impactdev.impactor.fabric;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.events.ImpactorEventBus;
import net.impactdev.impactor.api.platform.Platform;
import net.impactdev.impactor.api.plugin.ImpactorPlugin;
import net.impactdev.impactor.core.modules.ImpactorModule;
import net.impactdev.impactor.core.modules.ModuleInitializer;
import net.impactdev.impactor.core.plugin.ImpactorBootstrapper;
import net.impactdev.impactor.fabric.commands.FabricCommandModule;
import net.impactdev.impactor.fabric.integrations.PlaceholderAPIIntegration;
import net.impactdev.impactor.fabric.platform.FabricPlatformModule;
import net.impactdev.impactor.fabric.scheduler.FabricSchedulerModule;
import net.impactdev.impactor.fabric.ui.FabricUIModule;
import net.impactdev.impactor.minecraft.plugin.GameImpactorPlugin;

import java.util.Set;

public final class FabricImpactorPlugin extends GameImpactorPlugin implements ImpactorPlugin {

    public FabricImpactorPlugin(ImpactorBootstrapper bootstrapper) {
        super(bootstrapper);
    }

    @Override
    public void construct() {
        super.construct();

        Platform platform = Impactor.instance().platform();
        if(platform.info().plugin("placeholder-api").isPresent()) {
            var papi = new PlaceholderAPIIntegration();
            papi.subscribe(this.logger(), ImpactorEventBus.bus());
        }
    }

    @Override
    protected ModuleInitializer registerModules() {
        return super.registerModules()
                .with(FabricSchedulerModule.class)
                .with(FabricUIModule.class)
                .with(FabricPlatformModule.class)
                .with(FabricCommandModule.class);
    }
}
