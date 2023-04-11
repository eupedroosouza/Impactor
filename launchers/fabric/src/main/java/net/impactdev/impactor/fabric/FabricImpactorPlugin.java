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

import io.leangen.geantyref.TypeToken;
import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.plugin.ImpactorPlugin;
import net.impactdev.impactor.core.commands.ImpactorCommandRegistry;
import net.impactdev.impactor.core.commands.parsers.CurrencyParser;
import net.impactdev.impactor.core.modules.ImpactorModule;
import net.impactdev.impactor.core.plugin.ImpactorBootstrapper;
import net.impactdev.impactor.fabric.commands.BrigadierMapper;
import net.impactdev.impactor.fabric.commands.FabricCommandManager;
import net.impactdev.impactor.fabric.commands.FabricCommandModule;
import net.impactdev.impactor.fabric.platform.FabricPlatformModule;
import net.impactdev.impactor.fabric.scheduler.FabricSchedulerModule;
import net.impactdev.impactor.fabric.ui.FabricUIModule;
import net.impactdev.impactor.minecraft.plugin.GameImpactorPlugin;
import net.kyori.adventure.key.Key;

import java.util.Set;

public final class FabricImpactorPlugin extends GameImpactorPlugin implements ImpactorPlugin {

    public FabricImpactorPlugin(ImpactorBootstrapper bootstrapper) {
        super(bootstrapper);
    }

    @Override
    public void construct() {
        super.construct();
    }

    @Override
    protected void registerCommandMappings(ImpactorCommandRegistry registry) {
        FabricCommandManager manager = (FabricCommandManager) registry.manager();
        BrigadierMapper mapper = manager.mapper();
        mapper.map(TypeToken.get(CurrencyParser.class), Key.key("minecraft:resource_location"), true);
    }

    @Override
    protected Set<Class<? extends ImpactorModule>> modules() {
        Set<Class<? extends ImpactorModule>> parent = super.modules();
        parent.add(FabricSchedulerModule.class);
        parent.add(FabricUIModule.class);
        parent.add(FabricPlatformModule.class);
        parent.add(FabricCommandModule.class);

        return parent;
    }

    @Override
    public void shutdown() {
        Impactor.instance().scheduler().shutdownExecutor();
    }
}
