package com.waicool20.skrypton.sikulix

import org.sikuli.script.Match

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class SKryptonMatch(match: Match, screen: SKryptonScreen): Match(match), SikuliInterface by SKryptonRegion(match, screen)
