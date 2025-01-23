package com.yugentech.shuki.data.utils

import com.yugentech.shuki.data.notes.Note
import com.yugentech.shuki.data.tasks.Task

object DummyData {
    val dummyNotes: List<Note> = listOf(
        Note(title = "Meeting Notes - Q1 Planning", content = "Discussed quarterly goals, budget allocation, and team expansion plans. Key points: increase market share by 15%, launch mobile app by March, hire 3 developers."),
        Note(title = "Project Ideas", content = "1. Task management app\n2. Fitness tracker\n3. Recipe organizer\n4. Budget planner\n5. Language learning platform"),
        Note(title = "Shopping List", content = "- Groceries\n- Office supplies\n- New laptop charger\n- Headphones\n- Desk lamp"),
        Note(title = "Book Recommendations", content = "1. Atomic Habits\n2. Deep Work\n3. The Psychology of Money\n4. Clean Code\n5. Design Patterns"),
        Note(title = "Daily Journal - Jan 20", content = "Productive day at work. Completed the main features for the new update. Evening workout was great."),
        Note(title = "Website Redesign Ideas", content = "- Minimize cluttering\n- Add dark mode\n- Improve mobile responsiveness\n- Update color scheme\n- Optimize loading speed"),
        Note(title = "Learning Goals 2025", content = "1. Master Kotlin Multiplatform\n2. Learn SwiftUI\n3. Improve system design skills\n4. Study ML basics"),
        Note(title = "Recipe - Pasta Carbonara", content = "Ingredients:\n- Spaghetti\n- Eggs\n- Pecorino Romano\n- Pancetta\n- Black pepper"),
        Note(title = "Workout Plan", content = "Monday: Upper body\nTuesday: Lower body\nWednesday: Cardio\nThursday: Core\nFriday: Full body"),
        Note(title = "Travel Plans", content = "Places to visit:\n1. Japan\n2. Norway\n3. New Zealand\n4. Iceland\n5. Switzerland"),
        Note(title = "Article Ideas", content = "1. Modern Android Development\n2. Kotlin vs Swift\n3. Clean Architecture\n4. UI/UX Best Practices"),
        Note(title = "Home Improvement", content = "- Paint living room\n- Fix kitchen cabinet\n- Replace bathroom tiles\n- Update lighting"),
        Note(title = "Movie Watchlist", content = "1. Inception\n2. The Matrix\n3. Interstellar\n4. The Social Network\n5. Ex Machina"),
        Note(title = "Gift Ideas", content = "Mom: Cookbook\nDad: Watch\nSister: Headphones\nBrother: Gaming console"),
        Note(title = "Conference Notes", content = "KotlinConf 2024:\n- New language features\n- Performance improvements\n- Community updates"),
        Note(title = "Business Ideas", content = "1. Mobile app development agency\n2. Tech consulting\n3. Online courses\n4. SaaS products"),
        Note(title = "Personal Goals", content = "1. Read 24 books\n2. Learn piano\n3. Run a marathon\n4. Launch side project"),
        Note(title = "Project Timeline", content = "Phase 1: Planning (2 weeks)\nPhase 2: Development (8 weeks)\nPhase 3: Testing (4 weeks)"),
        Note(title = "Coding Resources", content = "- Kotlin docs\n- Android developers\n- Stack Overflow\n- GitHub\n- Medium articles"),
        Note(title = "Meeting Agenda", content = "1. Project updates\n2. Team feedback\n3. Resource allocation\n4. Next sprint planning")
    )
    val dummyTasks: List<Task> = listOf(
        Task(title = "Complete Project Proposal", description = "Write and review the proposal for the new mobile app project", isCompleted = false),
        Task(title = "Update Documentation", description = "Update the API documentation with new endpoints", isCompleted = true),
        Task(title = "Bug Fixes", description = "Fix reported crashes in the production build", isCompleted = false),
        Task(title = "Team Meeting", description = "Weekly sync-up with the development team", isCompleted = false),
        Task(title = "Code Review", description = "Review pull requests from junior developers", isCompleted = true),
        Task(title = "Update Dependencies", description = "Update all project dependencies to latest versions", isCompleted = false),
        Task(title = "Write Unit Tests", description = "Add unit tests for new features", isCompleted = false),
        Task(title = "UI Implementation", description = "Implement new design system components", isCompleted = true),
        Task(title = "Performance Optimization", description = "Optimize app loading time and memory usage", isCompleted = false),
        Task(title = "Client Meeting", description = "Discussion about project requirements", isCompleted = true),
        Task(title = "Database Migration", description = "Migrate to the new database schema", isCompleted = false),
        Task(title = "Setup CI/CD", description = "Configure automated build and deployment", isCompleted = true),
        Task(title = "Security Audit", description = "Perform security assessment of the app", isCompleted = false),
        Task(title = "User Testing", description = "Conduct user testing for new features", isCompleted = false),
        Task(title = "Analytics Integration", description = "Implement crash reporting and analytics", isCompleted = true),
        Task(title = "Localization", description = "Add support for multiple languages", isCompleted = false),
        Task(title = "Backup System", description = "Implement automated backup system", isCompleted = true),
        Task(title = "API Integration", description = "Integrate third-party payment API", isCompleted = false),
        Task(title = "Dark Mode", description = "Implement dark mode support", isCompleted = true),
        Task(title = "Release Preparation", description = "Prepare app for production release", isCompleted = false)
    )
}