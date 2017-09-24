package com.waicool20.skrypton.sikulix

import org.sikuli.script.Match

class SKryptonMatch(match: Match, screen: SKryptonScreen): Match(match), SikuliInterface by SKryptonRegion(match, screen)
